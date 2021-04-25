package com.avanza.unison.tools;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class XPathManager {
	
    private XPathManager () { // private constructor
        //myStaticMember = 1;
    }
    
    private static Document xmlDocument;
    private static XPath xPath;
    
	public static void Load() throws ParserConfigurationException, SAXException, IOException
	{
		String metaStructureFileName = System.getProperty("user.dir") + "\\MetaStructure.xml";
		File fileIS = new File(metaStructureFileName);
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		xmlDocument = builder.parse(fileIS);
		xPath = XPathFactory.newInstance().newXPath();
	}
	
	public static NodeList Evaluate(String expression) throws XPathExpressionException {
		NodeList nodeList = null;

		nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

		return nodeList;
	}
	
	

}
