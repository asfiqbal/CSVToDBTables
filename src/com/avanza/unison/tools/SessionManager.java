package com.avanza.unison.tools;

import java.util.HashMap;
import java.util.LinkedList;

public class SessionManager {
	
	UnisonConfigurationManager unisonConfigManager = null;
	
	LinkedList<HashMap<String, String>> listMap = new LinkedList<HashMap<String, String>>();
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
	
	
	
	public void Push(HashMap<String, String> tMap) {
		listMap.push(tMap);
	}

	public HashMap<String, String> Pop() {
		try {
			return listMap.pop();
		}
		catch(Exception e) {
			return null;
		}
	}

}
