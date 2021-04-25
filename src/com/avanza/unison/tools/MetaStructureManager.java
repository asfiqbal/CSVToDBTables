package com.avanza.unison.tools;

import org.w3c.dom.NodeList;

public final class MetaStructureManager {
	
    private MetaStructureManager () { // private constructor
        //myStaticMember = 1;
    }

    
	public static void Load()
	{
		try {
	
			XPathManager.Load();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
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
