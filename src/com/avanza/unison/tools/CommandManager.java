package com.avanza.unison.tools;



import java.util.HashMap;
import com.avanza.unison.tools.objects.Command;



public class CommandManager {
	
	public static Boolean IsCommand(String value) {
		String regEx = "([a-z]|[A-Z])\\w+\\('[a-z]+'\\)";
		return Util.Match(regEx, value);
	}
	
	public static String ExecuteDateTime(String param) {
		String returnValue = "";
		if (param.equalsIgnoreCase("Now")) {
			returnValue = Util.getNowInISODateFormat();
		}
		
		return returnValue;
		
	}


	public static String Execute(Command cmdObj, HashMap<String, String> dataMap, SequenceManager seqManager) {
		String returnValue = "";
		String key = cmdObj.getName();
		switch(key) {
		case "GenerateSequence":
			returnValue = seqManager.Generate(cmdObj.getParam());
			dataMap.put(cmdObj.getParam(), returnValue);
			break;
		case "GetInputField":
		case "GetSequence":
			returnValue = dataMap.get(cmdObj.getParam());
			break;
			
		case "DateTime":
			returnValue = ExecuteDateTime(cmdObj.getParam());
			break;
		}
		return returnValue;
	}

}
