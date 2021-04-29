package com.avanza.unison.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.avanza.unison.tools.objects.Command;

public class Util {
	
	public static Boolean Match(String expr, String value) {
		Pattern pattern = Pattern.compile(expr, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(value);
	    boolean matchFound = matcher.find();
	    return matchFound;
	   
	}
	
	public static String ExtractDigit(String value) {
		String regEx = "\\d+";
		Pattern r = Pattern.compile(regEx);
		Matcher m = r.matcher(value);
		String returnValue = "";

		if (m.find( )) {
			returnValue = m.group(0);
	    }
		return returnValue;
	   
	}

	public static Command BreakCommand(String value) {
		String regEx = "(^(\\w)+)(\\W+)(\\w+)";
		Pattern r = Pattern.compile(regEx);
		Matcher m = r.matcher(value);

		Command cmdObj = new Command();
		if (m.find( )) {
			cmdObj.setName(m.group(1));
			cmdObj.setParam(m.group(4));
	    }

		return cmdObj;
	}
	
	public static String SQLQuote(String value, String type) {
		
		String returnValue = "";
		switch(type) {
		case "String": 
		case "string": 
			returnValue = "\"" +  value + "\"";
			break;
			
		case "DateTime": 
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
			LocalDateTime localDateTime = LocalDateTime.parse(value, formatter);
			String formatDateTime = localDateTime.format(formatter);
			returnValue = "\"" +  formatDateTime + "\"";
			break;
			
		default:
			returnValue = value;
			
		}
		return returnValue;

	}
	
	public static String getNowInISODateFormat() {
		LocalDateTime ldt = LocalDateTime.now();
		String str = ldt.format(DateTimeFormatter.ISO_DATE_TIME);
		return str;
	}

	public static String getXPathFromRoot(Element activeElement) {
		Node parentNode = activeElement.getParentNode();
		if (parentNode instanceof Document)
			return "//" + activeElement.getTagName();
		String outString = getXPathFromRoot((Element)parentNode);
		outString = outString + "//" + activeElement.getTagName();
		return outString;
	}
}
