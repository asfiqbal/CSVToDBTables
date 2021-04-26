package com.avanza.unison.tools.objects;

public class Sequence {
	String name;
	String template;
	int increment;
	long start;
	
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
	public long getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = Long.parseLong(start);
	}
	public void setStart(long intStart) {
		this.start = intStart;
	}

}
