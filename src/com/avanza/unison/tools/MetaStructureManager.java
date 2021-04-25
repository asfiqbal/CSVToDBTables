package com.avanza.unison.tools;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class MetaStructureManager {
	
    private MetaStructureManager () { // private constructor
        //myStaticMember = 1;
    }

    
	public static void Load() throws ParserConfigurationException, SAXException, IOException
	{
		XPathManager.Load();
	}
	
	public static NodeList LoadSequences()
	{
		NodeList nodeList = null;
		try {
	
			String expression = "/xml/sequence/Generate";
			nodeList = XPathManager.Evaluate(expression);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return nodeList;
		
	}
	
	public static NodeList LoadInputFields()
	{
		NodeList nodeList = null;
		try {
	
			String expression = "/xml/RowInputStructure/InputField";
			nodeList = XPathManager.Evaluate(expression);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return nodeList;
		
	}
	
	public static NodeList LoadOutputStructure()
	{
		NodeList nodeList = null;
		try {
	
			String expression = "/xml/OutputStructure/Table";
			nodeList = XPathManager.Evaluate(expression);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return nodeList;
		
	}
	
	

}
