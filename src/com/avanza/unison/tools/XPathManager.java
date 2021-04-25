package com.avanza.unison.tools;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public final class XPathManager {
	
    private XPathManager () { // private constructor
        //myStaticMember = 1;
    }
    
    private static Document xmlDocument;
    private static XPath xPath;
    
	public static void Load()
	{
		try {
	
			String metaStructureFileName = System.getProperty("user.dir") + "\\MetaStructure.xml";
			File fileIS = new File(metaStructureFileName);
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			xmlDocument = builder.parse(fileIS);
			xPath = XPathFactory.newInstance().newXPath();
			//String expression = "/Tutorials/Tutorial";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static NodeList Evaluate(String expression) {
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodeList;
	}
	
	

}
