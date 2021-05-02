package com.avanza.unison.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.avanza.unison.tools.objects.OutField;
import com.avanza.unison.tools.objects.SQLCollection;
import com.avanza.unison.tools.objects.Table;


public class OutputStructureManager {
	
	HashMap<String, Table> tableMap = new LinkedHashMap<String, Table>();
	
	
	public void Load(NodeList nodeList) throws XPathExpressionException
	{
		for (int i = 0; i < nodeList.getLength(); i++) {
			
			Node node = nodeList.item(i);
			
			

			Table tableObj = new Table();
			Element element = (Element)node;
			String mapName = element.getAttribute("name");
			tableObj.setName(mapName);
			tableObj.setInsertSQL(element.getAttribute("insertSQL"));
			tableObj.setInsertSQL(element.getAttribute("deleteSQL"));
			tableObj.setPostSQL(element.getAttribute("postSQL"));
			
			String insertAuditField = element.getAttribute("insertAuditField");
			tableObj.setInsertAuditField(insertAuditField);
			tableObj.setAuditUser(element.getAttribute("auditUser"));
						
			String xPathXpr = "//OutputStructure//Table[@name='" + mapName+"']/OutField";

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

			xPathXpr = "//OutputStructure//Table[@name='" + mapName+"']/Table";

			NodeList outTablesList = XPathManager.Evaluate(xPathXpr);

			if (outTablesList.getLength() > 0)
				Load(outTablesList);

			tableMap.put(mapName, tableObj);
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
		for (Map.Entry<String, Table> me : tableMap.entrySet()) {
			Table table = (Table)me.getValue();
			System.out.println("Table Name: "+me.getKey());
			table.Traverse();
	    }
		
	}
	
	public void Process(SessionManager sessionManager, String filePath) {
		HashMap<String, String> dataMap = null;
		ArrayList<SQLCollection> sqlSet = new ArrayList<SQLCollection>();
		TableManager tableManager = new TableManager();
		
		while ((dataMap = sessionManager.Pop()) != null) {

			for(Map.Entry<String, Table> entry: tableMap.entrySet()) {

				Table tableObj = entry.getValue();
				SQLCollection sqls = tableManager.Process(tableObj, dataMap, sessionManager.getInternalSeqManager());
				sqlSet.add(sqls);
			}
		}
		
		FileManager.WriteListToFile(sqlSet, filePath);

	}


}
