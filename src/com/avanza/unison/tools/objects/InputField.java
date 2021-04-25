package com.avanza.unison.tools.objects;

public class InputField {
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = Integer.parseInt(position);
	}
	public String getValidationExpr() {
		return validationExpr;
	}
	public void setValidationExpr(String validationExpr) {
		this.validationExpr = validationExpr;
	}
	public int getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = Integer.parseInt(validate);
	}
	String name;
	String type;
	int position;
	String validationExpr;
	int validate;
	

}
