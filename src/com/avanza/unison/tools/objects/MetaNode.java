package com.avanza.unison.tools.objects;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MetaNode {
	String Name;

	
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



	

	
	
}
