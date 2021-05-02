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


	public static String Execute(Command cmdObj, HashMap<String, String> dataMap, SessionManager sessionManager) {
		String returnValue = "";
		String key = cmdObj.getName();
		String keyValue = "";
		switch(key) {
		case "GenerateSequence":
			returnValue = sessionManager.internalSeqManager.Generate(cmdObj.getParam());
			dataMap.put(cmdObj.getParam(), returnValue);
			break;
		case "GetInputField":
			returnValue = dataMap.get(cmdObj.getParam());
			break;
			
		case "GetSequence":
			keyValue = dataMap.get(cmdObj.getParam());
			returnValue = sessionManager.getSessionValue(keyValue);
			if (returnValue == null) {
				returnValue = keyValue;
			}
			System.out.println("ObjParam:" + cmdObj.getParam() + " ,keyValue=" + keyValue + " ,returnValue:"+returnValue);
			break;
			
		case "GetFromGlobalStore":
			String paramName = cmdObj.getParam();
			keyValue = dataMap.get(paramName);
			
			returnValue = sessionManager.getSessionValue(paramName + "|" + keyValue);
			if (returnValue == null) {
				returnValue = keyValue;
			}
			System.out.println("ObjParam:" + cmdObj.getParam() + " ,keyValue=" + keyValue + " ,returnValue:"+returnValue);
			break;

		case "DateTime":
			returnValue = ExecuteDateTime(cmdObj.getParam());
			break;
		}
		return returnValue;
	}

}
