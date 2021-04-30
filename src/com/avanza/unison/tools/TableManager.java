package com.avanza.unison.tools;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avanza.unison.tools.objects.Command;
import com.avanza.unison.tools.objects.OutField;
import com.avanza.unison.tools.objects.Table;

public class TableManager {
	
	public String Process(Table table, HashMap<String, String> dataMap, SequenceManager seqManager) {
		HashMap<String, OutField> outFieldMap = table.getFieldsMap();
		HashMap<String, Table> outInnerTableMap = table.getTableMap();
		String sql = table.getPreSQL();
		String sqlColumnNames = "";
		String sqlValues = "";
		
		List<String> columnNames = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
					
		for (Map.Entry<String, OutField> me : outFieldMap.entrySet()) {
			OutField field = (OutField)me.getValue();
			columnNames.add(field.getName());
			String value = field.getValue();
			if (CommandManager.IsCommand(value)) {
				Command cmdObj = Util.BreakCommand(value);
				value = CommandManager.Execute(cmdObj, dataMap, seqManager);
				
			}
			
			values.add(Util.SQLQuote(value, field.getType()));
	    }
		
		sqlColumnNames = String.join(",", columnNames);
		sqlValues = String.join(",", values);
		
		String outSql = String.format(sql, table.getName(), sqlColumnNames, sqlValues);
		StringBuilder sb = new StringBuilder();
		sb.append(outSql);
		sb.append(table.getPostSQL());
		sb.append("\n");

		for (Map.Entry<String, Table> me : outInnerTableMap.entrySet()) {
			Table t = (Table)me.getValue();
			outSql = Process(t, dataMap, seqManager);
			sb.append(outSql);
	    }
		
		return sb.toString();
	}

}
