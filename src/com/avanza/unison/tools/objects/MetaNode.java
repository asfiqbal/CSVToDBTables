package com.avanza.unison.tools.objects;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.avanza.unison.tools.RowInputFieldManager;
import com.avanza.unison.tools.SessionManager;
import com.avanza.unison.tools.TableManager;
import com.avanza.unison.tools.UnisonConfigurationManager;
import com.avanza.unison.tools.Util;
import com.avanza.unison.tools.XPathManager;
import com.sun.media.sound.InvalidFormatException;

public class MetaNode {
	String Name;
	
	Element tagElement = null;

	
	HashMap<String, InputField> InputFieldMap = new LinkedHashMap<String, InputField>();
	HashMap<String, Table> tableMap = new LinkedHashMap<String, Table>();
	HashMap<String, MetaNode> innerMetaNodeMap = new LinkedHashMap<String, MetaNode>();
	

	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		Name = name;
	}
	
	
	public InputField getInputField(String key) {
		return InputFieldMap.get(key);
	}
	
	public void putInputField(String key, InputField T) {
		InputFieldMap.put(key, T);
	}

	public Table getTable(String key) {
		return tableMap.get(key);
	}
	
	public void putTable(String key, Table T) {
		tableMap.put(key, T);
	}
	
	public MetaNode getMetaNode(String key) {
		return innerMetaNodeMap.get(key);
	}
	
	public void putMetaNode(String key, MetaNode T) {
		innerMetaNodeMap.put(key, T);
	}


	public void Traverse()	{
		for (Map.Entry<String, InputField> me : InputFieldMap.entrySet()) {
			InputField field = (InputField)me.getValue();
			System.out.println("Name: "+field.getName()+", Type: "+field.getType()+", Position: "+field.getPosition());
	    }
		
		for (Map.Entry<String, Table> me : tableMap.entrySet()) {
			Table field = (Table)me.getValue();
			System.out.println("Name: "+field.getName()+", PostSQL: "+field.getPostSQL()+", PreSQL: "+field.getPreSQL());
			
			field.Traverse();
	    }
		
		for (Map.Entry<String, MetaNode> me : innerMetaNodeMap.entrySet()) {
			MetaNode table = (MetaNode)me.getValue();
			table.Traverse();
	    }
	}

	public void ValidateInputFields(HashMap<String, String> configInputDataMap) throws InvalidFormatException {
		
		for(Map.Entry<String, InputField> entry: InputFieldMap.entrySet()) {
			String fieldName = entry.getKey();
			InputField fieldObj = entry.getValue();
			int pos = fieldObj.getPosition();
			String columnValue = configInputDataMap.get(fieldName);
			int validate = fieldObj.getValidate();
			if (validate == 1) {
				String validateExpr = fieldObj.getValidationExpr();
				if (!Util.Match(validateExpr, columnValue))
					throw new InvalidFormatException();
			}
		}

	}
	
	public String Process(SessionManager inSessionManager, Node parentNode) throws XPathExpressionException, InvalidFormatException	{
		String outString = "";
		String XpathExpression ="";
		StringBuilder sqlStringBuilder = new StringBuilder();

		UnisonConfigurationManager unisonConfigReader = inSessionManager.getUnisonConfigurationManager();
		
		if (parentNode == null) {
			XpathExpression = "//UnisonConfiguration//" + Name;
		}
		else {
			String value = parentNode.getAttributes().getNamedItem("Id").getNodeValue();
			XpathExpression = "//UnisonConfiguration//"+parentNode.getNodeName()+"[@Id='" + value + "']//" + Name;
		}
		NodeList metaEntityDataList = unisonConfigReader.Evaluate(XpathExpression);
		
		HashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		
		for (int i = 0; i < metaEntityDataList.getLength(); i++) {	
			Node singleMetaEntity = metaEntityDataList.item(i);

			Element element = (Element)singleMetaEntity;
			int numAttrs = element.getAttributes().getLength();
			
			for (int k = 0; k < numAttrs; k++) {
			   Attr attr = (Attr) element.getAttributes().item(k);
			   String attrName = attr.getNodeName();
			   String attrValue = attr.getNodeValue();
			   dataMap.put(attrName, attrValue);
			}
		
			ValidateInputFields(dataMap);
			
			for (Map.Entry<String, Table> me : tableMap.entrySet()) {
				Table tableObj = (Table)me.getValue();
				
				TableManager tableManager = new TableManager();
				String tempSQL = tableManager.Process(tableObj, dataMap, inSessionManager.getInternalSeqManager());
				sqlStringBuilder.append(tempSQL);
				
		    }
		
			for (Map.Entry<String, MetaNode> me : innerMetaNodeMap.entrySet()) {
				MetaNode childMetaNode = (MetaNode)me.getValue();
				//childMetaNode.Traverse();
				String childSQL = childMetaNode.Process(inSessionManager, singleMetaEntity);
				sqlStringBuilder.append(childSQL);
		    }
		
		}
		return sqlStringBuilder.toString();
	}


	public void LoadTables() throws XPathExpressionException
	{
		String xPath = Util.getXPathFromRoot(tagElement);
		
		String xPathXpr = xPath + "[@name='" + Name + "']/OutputStructure/Table";

		NodeList tableNodes = XPathManager.Evaluate(xPathXpr);		 
		for (int i = 0; i < tableNodes.getLength(); i++) {
			
			Node node = tableNodes.item(i);
			
			Table tableObj = new Table();
			Element element = (Element)node;
			String tableName = element.getAttribute("name");
			tableObj.setName(tableName);
			tableObj.setPreSQL(element.getAttribute("preSQL"));
			tableObj.setPostSQL(element.getAttribute("postSQL"));
			
			String insertAuditField = element.getAttribute("insertAuditField");
			tableObj.setInsertAuditField(insertAuditField);
			tableObj.setAuditUser(element.getAttribute("auditUser"));
	
			xPathXpr =  xPathXpr + "[@name='" + tableName+ "']/OutField";

			NodeList outFieldsList = XPathManager.Evaluate(xPathXpr);

			for (int n = 0; n < outFieldsList.getLength() ; n++) {
			    Node child = outFieldsList.item(n);
				Element innerElement = (Element)child;
				
			    OutField outField = new OutField();
				String outFieldName = innerElement.getAttribute("name");
				outField.setName(outFieldName);
				outField.setType(innerElement.getAttribute("type"));
				outField.setValue(innerElement.getAttribute("value"));
				tableObj.putOutField(outFieldName, outField);
			}
			
			
			if (insertAuditField.equalsIgnoreCase("1")) {
				InsertAuditFields(tableObj);
			}
						
			tableMap.put(tableName, tableObj);

		}

	}
	
	public void LoadChildMetaNodes() throws XPathExpressionException
	{
		String xPath = Util.getXPathFromRoot(tagElement);
		
		String xPathXpr = xPath + "[@name='" + Name + "']/MetaNode";

		NodeList childMetaNodes = XPathManager.Evaluate(xPathXpr);

		for (int i = 0; i < childMetaNodes.getLength(); i++) {
			
			Node node = childMetaNodes.item(i);
			
			MetaNode metaObj = new MetaNode();

			Element element = (Element)node;
			String name = element.getAttribute("name");
			metaObj.setName(name);
			innerMetaNodeMap.put(name, metaObj);
			
			metaObj.LoadAllDependencies(element);
		}

	}
	
	public void LoadInputFields() throws XPathExpressionException
	{
		String xPath = Util.getXPathFromRoot(tagElement);
		
		String xPathXpr = xPath + "[@name='" + Name + "']/RowInputStructure/InputField";

		NodeList nodeList = XPathManager.Evaluate(xPathXpr);
	
		for (int i = 0; i < nodeList.getLength(); i++) {
			
			Node node = nodeList.item(i);

			InputField inputField = new InputField();
			Element element = (Element)node;
			String mapName = element.getAttribute("name");
			inputField.setName(mapName);
			inputField.setType(element.getAttribute("type"));
			inputField.setPosition(element.getAttribute("position"));
			inputField.setValidationExpr(element.getAttribute("validationExpr"));
			inputField.setValidate(element.getAttribute("validate"));
			
			InputFieldMap.put(mapName, inputField);
		}

	}
	
	public void LoadAllDependencies(Element activeElement) throws XPathExpressionException
	{
		tagElement = activeElement;
		LoadInputFields();

		LoadTables();
		
		LoadChildMetaNodes();
	}
	
	private void InsertAuditFields(Table tableObj) {
		OutField outField = new OutField();
		String outFieldName = "Created_By";
		outField.setName(outFieldName);
		outField.setType("String");
		outField.setValue(tableObj.getAuditUser());
		tableObj.putOutField(outFieldName, outField);
		
		outField = new OutField();
		outFieldName = "Created_On";
		outField.setName(outFieldName);
		outField.setType("DateTime");
		outField.setValue("DateTime('Now')");
		tableObj.putOutField(outFieldName, outField);
		
		outField = new OutField();
		outFieldName = "Updated_By";
		outField.setName(outFieldName);
		outField.setType("String");
		outField.setValue(tableObj.getAuditUser());
		tableObj.putOutField(outFieldName, outField);

		outField = new OutField();
		outFieldName = "Updated_On";
		outField.setName(outFieldName);
		outField.setType("DateTime");
		outField.setValue("DateTime('Now')");
		tableObj.putOutField(outFieldName, outField);
	}


	
	
}
