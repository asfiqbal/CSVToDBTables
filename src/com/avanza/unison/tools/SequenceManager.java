package com.avanza.unison.tools;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.avanza.unison.tools.objects.Sequence;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SequenceManager  {

	HashMap<String, AlphaNumSequenceGenrator> sequenceMap = new LinkedHashMap<String, AlphaNumSequenceGenrator>();
	
	public void Load(NodeList nodeList)
	{
		for (int i = 0; i < nodeList.getLength(); i++) {
			
			Node node = nodeList.item(i);

			Sequence sequence = new Sequence();
			Element generateNode = (Element)node;
			String mapName = generateNode.getAttribute("name");
			sequence.setName(mapName);
			sequence.setTemplate(generateNode.getAttribute("template"));
			sequence.setIncrement(generateNode.getAttribute("increment"));
			sequence.setStart(generateNode.getAttribute("start"));
			
			AlphaNumSequenceGenrator alphaSeqGen = new AlphaNumSequenceGenrator(sequence);
			
			sequenceMap.put(mapName, alphaSeqGen);
		}

	}
	
	public String Generate(String key)
	{
		AlphaNumSequenceGenrator seqGenerator = sequenceMap.get(key);
		String value = seqGenerator.Generate();
		return value;
	}

}
