package com.avanza.unison.tools.objects;

public class Sequence {
	String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public int getIncrement() {
		return increment;
	}
	public void setIncrement(String increment) {
		this.increment = Integer.parseInt(increment);
	}
	public int getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = Integer.parseInt(start);
	}
	public void setStart(int intStart) {
		this.start = intStart;
	}
	String template;
	int increment;
	int start;
}
