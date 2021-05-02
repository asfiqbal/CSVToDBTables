package com.avanza.unison.tools.objects;

public class SQLCollection {
	private String InsertSQL;
	private String DeleteSQL;
	
	public SQLCollection(String insert, String delete) {
		InsertSQL = insert;
		DeleteSQL = delete;
	}
	
	public SQLCollection() {
		InsertSQL = new String();
		DeleteSQL = new String();
	}

	public String getInsertSQL() {
		return InsertSQL;
	}
	public void setInsertSQL(String insertSQL) {
		InsertSQL = insertSQL;
	}
	public String getDeleteSQL() {
		return DeleteSQL;
	}
	public void setDeleteSQL(String deleteSQL) {
		DeleteSQL = deleteSQL;
	}
	
	public void append(SQLCollection in) {
		InsertSQL = InsertSQL + in.getInsertSQL();
		DeleteSQL = DeleteSQL + in.getDeleteSQL();
	}
	
	public void append(String insert, String delete) {
		InsertSQL = InsertSQL + insert;
		DeleteSQL = DeleteSQL + delete;
	}
}
