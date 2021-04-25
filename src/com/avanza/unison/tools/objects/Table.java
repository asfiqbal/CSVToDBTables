package com.avanza.unison.tools.objects;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Table {
	String Name;
	String PreSQL;
	String PostSQL;
	String insertAuditField;
	String auditUser;
	
	HashMap<String, OutField> outFieldMap = new LinkedHashMap<String, OutField>();
	HashMap<String, Table> innerTableMap = new LinkedHashMap<String, Table>();
	
	public String getInsertAuditField() {
		return insertAuditField;
	}

	public void setInsertAuditField(String insertAuditField) {
		this.insertAuditField = insertAuditField;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}



	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		Name = name;
	}
	
	public String getPreSQL() {
		return PreSQL;
	}
	
	public void setPreSQL(String preSQL) {
		PreSQL = preSQL;
	}
	
	public String getPostSQL() {
		return PostSQL;
	}
	
	public void setPostSQL(String postSQL) {
		PostSQL = postSQL;
	}
	
	public Table getTable(String key) {
		return innerTableMap.get(key);
	}
	
	public void putTable(String key, Table T) {
		innerTableMap.put(key, T);
	}
	
	public OutField getOutField(String key) {
		return outFieldMap.get(key);
	}
	
	public void putOutField(String key, OutField T) {
		outFieldMap.put(key, T);
	}

	public void Traverse()	{
		for (Map.Entry<String, OutField> me : outFieldMap.entrySet()) {
			OutField field = (OutField)me.getValue();
			System.out.println("Name: "+field.getName()+", Type: "+field.getType()+", Value: "+field.getValue());
	    }
		
		for (Map.Entry<String, Table> me : innerTableMap.entrySet()) {
			Table table = (Table)me.getValue();
			table.Traverse();
	    }
	}

	public HashMap<String, OutField> getFieldsMap() {
		
		return outFieldMap;
	}
	
	public HashMap<String, Table> getTableMap() {
		
		return innerTableMap;
	}
	
	
}
