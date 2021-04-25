package com.avanza.unison.tools;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.avanza.unison.tools.objects.InputField;
import com.sun.media.sound.InvalidFormatException;

public class RowInputFieldManager {
	
	HashMap<String, InputField> inputFieldMap = new HashMap<String, InputField>();
	
	
	public void Load(NodeList nodeList)
	{
		for (int i = 0; i < nodeList.getLength(); i++) {
			
			Node node = nodeList.item(i);

			InputField inputField = new InputField();
			Element element = (Element)node;
			String mapName = element.getAttribute("name");
			inputField.setName(mapName);
			inputField.setType(element.getAttribute("type"));
			inputField.setPosition(element.getAttribute("position"));
			inputField.setValidationExpr(element.getAttribute("validationExpr"));
			inputField.setValidate(element.getAttribute("validate"));
			
			inputFieldMap.put(mapName, inputField);
		}

	}
	
	public HashMap<String, String> Process(String[] fileRow) throws InvalidFormatException {
		HashMap<String, String> tMap = new HashMap<String, String>();
		for(Map.Entry<String, InputField> entry: inputFieldMap.entrySet()) {
			String fieldName = entry.getKey();
			InputField fieldObj = entry.getValue();
			int pos = fieldObj.getPosition();
			String columnValue = fileRow[pos];
			int validate = fieldObj.getValidate();
			if (validate == 1) {
				String validateExpr = fieldObj.getValidationExpr();
				if (!Util.Match(validateExpr, columnValue))
					throw new InvalidFormatException();
			}
			tMap.put(fieldName, columnValue.trim());
		}
		return tMap;
	}

}
