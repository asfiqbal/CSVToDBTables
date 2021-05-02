package com.avanza.unison.tools;

import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.avanza.unison.tools.objects.MetaNode;

public class SessionManager {
	
	UnisonConfigurationManager unisonConfigManager = null;
	
	HashMap<String, String> sessionKeys = new HashMap<String, String>();
	SequenceManager internalSeqManager = null;
	
	public SequenceManager getInternalSeqManager() {
		return internalSeqManager;
	}

	public SessionManager(SequenceManager seqManager, UnisonConfigurationManager configManager) {
		internalSeqManager = seqManager;
		unisonConfigManager = configManager;
	}
	
	public UnisonConfigurationManager getUnisonConfigurationManager() {
		return unisonConfigManager;
	}
	
	public void GenerateIds() throws XPathExpressionException {
		String xpathExpr = "//*[@InternalReferenceKey!='']";

		NodeList nodeList = unisonConfigManager.Evaluate(xpathExpr);

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			Element element = (Element)node;
			String tagName = element.getTagName();
			String key = element.getAttribute("InternalReferenceKey");
			System.out.println("tagName:" + tagName + " ,key " + key );
			String metaId = "";
			try {
				metaId = internalSeqManager.Generate(tagName);
			} catch(Exception e) {
				metaId = key;
			}
			System.out.println("key:" + key + " ,metaId" + metaId );
			putSessionKey(key, metaId);
		}
	}
	
	public void putSessionKey(String key, String value) {
		sessionKeys.put(key, value);
	}

	public String getSessionValue(String key) {
		try {
			return sessionKeys.get(key);
		}
		catch(Exception e) {
			return null;
		}
	}

}
