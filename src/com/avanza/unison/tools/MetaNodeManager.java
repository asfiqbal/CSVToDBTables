package com.avanza.unison.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.avanza.unison.tools.objects.InputField;
import com.avanza.unison.tools.objects.MetaNode;
import com.avanza.unison.tools.objects.OutField;
import com.avanza.unison.tools.objects.Table;


public class MetaNodeManager {
	
	HashMap<String, MetaNode> metaNodeMap = new LinkedHashMap<String, MetaNode>();
	
	public void LoadTables(MetaNode activeNode, NodeList nodeList) throws XPathExpressionException
	{
		 
		for (int i = 0; i < nodeList.getLength(); i++) {
			
			Node node = nodeList.item(i);
			
			Table tableObj = new Table();
			Element element = (Element)node;
			String tableName = element.getAttribute("name");
			tableObj.setName(tableName);
			tableObj.setPreSQL(element.getAttribute("preSQL"));
			tableObj.setPostSQL(element.getAttribute("postSQL"));
			
			String insertAuditField = element.getAttribute("insertAuditField");
			tableObj.setInsertAuditField(insertAuditField);
			tableObj.setAuditUser(element.getAttribute("auditUser"));
	
			String xPathXpr = "//MetaNode[@name='" + activeNode.getName() + "']/OutputStructure/Table[@name='" + tableName+ "']/OutField";

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
			
			activeNode.putTable(tableName, tableObj);
			
			
			if (insertAuditField.equalsIgnoreCase("1")) {
				InsertAuditFields(tableObj);
			}

			xPathXpr = "//MetaNode[@name='" + activeNode.getName() + "']/OutputStructure/Table[@name='" + tableName+ "']/Table";

			NodeList outTablesList = XPathManager.Evaluate(xPathXpr);

			if (outTablesList.getLength() > 0)
				LoadTables(activeNode, outTablesList);

		}

	}
	
	public void LoadInputFields(MetaNode activeNode, String key) throws XPathExpressionException
	{
		String xPathXpr = "//MetaNode[@name='" + key + "']/RowInputStructure/InputField";

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
			
			activeNode.putInputField(mapName, inputField);
		}

	}
	
	public void Load(NodeList nodeList) throws XPathExpressionException
	{
		for (int i = 0; i < nodeList.getLength(); i++) {
			
			Node node = nodeList.item(i);
			
			MetaNode metaObj = new MetaNode();

			Element element = (Element)node;
			String name = element.getAttribute("name");
			metaObj.setName(name);
			
			LoadInputFields(metaObj, name);
			
			String xPathXpr = "//MetaNode[@name='" + name + "']/OutputStructure/Table";

			NodeList childTableNodes = XPathManager.Evaluate(xPathXpr);
			
			LoadTables(metaObj, childTableNodes);

			xPathXpr = "//MetaNode[@name='" + name + "']/MetaNode";

			NodeList childMetaNodes = XPathManager.Evaluate(xPathXpr);

			if (childMetaNodes.getLength() > 0)
				Load(childMetaNodes);

			metaNodeMap.put(name, metaObj);
		}

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

	public void Traverse()	{
		for (Map.Entry<String, MetaNode> me : metaNodeMap.entrySet()) {
			MetaNode metaNode = (MetaNode)me.getValue();
			System.out.println("MetaNode Name: "+me.getKey());
			metaNode.Traverse();
	    }
		
	}
	
	public void Process(SessionManager sessionManager, String filePath) {
//		HashMap<String, String> dataMap = null;
//		ArrayList<String> sqlSet = new ArrayList<String>();
//		TableManager tableManager = new TableManager();
//		
//		while ((dataMap = sessionManager.Pop()) != null) {
//
//			for(Map.Entry<String, Table> entry: tableMap.entrySet()) {
//
//				Table tableObj = entry.getValue();
//				String sql = tableManager.Process(tableObj, dataMap, sessionManager.getInternalSeqManager());
//				sqlSet.add(sql);
//			}
//			sqlSet.add("\n");
//		}
//		
//		FileManager.WriteListToFile(sqlSet, filePath);

	}


}
