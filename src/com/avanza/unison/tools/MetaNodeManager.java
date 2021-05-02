package com.avanza.unison.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.avanza.unison.tools.objects.MetaNode;

import com.avanza.unison.tools.objects.SQLCollection;

import com.sun.media.sound.InvalidFormatException;


public class MetaNodeManager {
	
	HashMap<String, MetaNode> metaNodeMap = new LinkedHashMap<String, MetaNode>();
	
	String unisonVersion;
	String root;
	
	public String getUnisonVersion() {
		return unisonVersion;
	}

	public void setUnisonVersion(String unisonVersion) {
		this.unisonVersion = unisonVersion;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}


	public MetaNodeManager() {
	}
	
	public void Load(NodeList nodeList) throws XPathExpressionException
	{
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			
			Node node = nodeList.item(i);
			
			MetaNode metaObj = new MetaNode();

			Element element = (Element)node;
			String name = element.getAttribute("name");
			metaObj.setName(name);
			metaNodeMap.put(name, metaObj);
			metaObj.LoadAllDependencies(element);
		}

	}
	
	public void Traverse()	{
		for (Map.Entry<String, MetaNode> me : metaNodeMap.entrySet()) {
			MetaNode metaNode = (MetaNode)me.getValue();
			System.out.println("MetaNode Name: "+me.getKey());
			metaNode.Traverse();
	    }
		
	}
	
	public void Process(SessionManager inSessionManager, String inFileName) throws InvalidFormatException, XPathExpressionException {
		HashMap<String, String> dataMap = null;
		ArrayList<SQLCollection> sqlSet = new ArrayList<SQLCollection>();
	
		for(Map.Entry<String, MetaNode> entry: metaNodeMap.entrySet()) {

			MetaNode metaNode = entry.getValue();
			SQLCollection sql = metaNode.Process(inSessionManager, null);
			sqlSet.add(sql);
		}
		
		System.out.println("Writing Generated SQL to " + inFileName + " File.");
		FileManager.WriteListToFile(sqlSet, inFileName);

	}

	public void LoadRoot(Node loadUnisonNode) {
		
		Element element = (Element)loadUnisonNode;
		root = element.getAttribute("root");
		unisonVersion = element.getAttribute("version");
	}


}
