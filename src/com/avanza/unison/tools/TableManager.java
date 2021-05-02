package com.avanza.unison.tools;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avanza.unison.tools.objects.Command;
import com.avanza.unison.tools.objects.OutField;
import com.avanza.unison.tools.objects.SQLCollection;
import com.avanza.unison.tools.objects.Table;

public class TableManager {
	
	public SQLCollection Process(Table table, HashMap<String, String> dataMap, SessionManager sessionManager) {
		HashMap<String, OutField> outFieldMap = table.getFieldsMap();
		HashMap<String, Table> outInnerTableMap = table.getTableMap();
		String insertSql = table.getInsertSQL();
		String deleteSqlTemplate = table.getDeleteSQL();
		String sqlColumnNames = "";
		String sqlValues = "";

		
		List<String> columnNames = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		List<String> deleteWhereConditions = new ArrayList<String>();

					
		for (Map.Entry<String, OutField> me : outFieldMap.entrySet()) {
			OutField field = (OutField)me.getValue();
			columnNames.add(field.getName());
			String value = field.getValue();
			if (CommandManager.IsCommand(value)) {
				Command cmdObj = Util.BreakCommand(value);
				value = CommandManager.Execute(cmdObj, dataMap, sessionManager);
				
			}
			value = Util.SQLQuote(value, field.getType());
			
			if (field.getDeleteKey().equalsIgnoreCase("Yes")) {
				String condition = field.getName() + "=" + value;
				deleteWhereConditions.add(condition);
			}
			
			values.add(value);
	    }
		
		sqlColumnNames = String.join(",", columnNames);
		sqlValues = String.join(",", values);
		String sqlFinalConditions = String.join(" AND ", deleteWhereConditions);
		
		String outSql = String.format(insertSql, table.getName(), sqlColumnNames, sqlValues);
		String deleteSQL = String.format(deleteSqlTemplate, table.getName(), sqlFinalConditions);
		StringBuilder sb = new StringBuilder();
		sb.append(outSql);
		sb.append(table.getPostSQL());
		sb.append("\n");
		
		deleteSQL = deleteSQL + table.getPostSQL() + "\n";

		SQLCollection sqlCollection = new SQLCollection(sb.toString(), deleteSQL);

		for (Map.Entry<String, Table> me : outInnerTableMap.entrySet()) {
			Table t = (Table)me.getValue();
			SQLCollection outSqls = Process(t, dataMap, sessionManager);
			sqlCollection.append(outSqls);
	    }
		
		
		return sqlCollection;
	}

}
