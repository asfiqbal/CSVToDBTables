/**
 * 
 */
package com.avanza.unison.tools;

import java.util.HashMap;

import com.sun.media.sound.InvalidFormatException;

/**
 * @author asif.iqbal
 *
 */
public class Root {
	
	public static void ShowLicense() {

		System.out.println("Unison Tools Set version 1.0");
		System.out.println("Perceived And Developed by Syed Asif Iqbal");
		System.out.println("");
	}
	
	
//	public static void ProcessInputFile(String fileName, SessionManager sessionManager, RowInputFieldManager rowInputFieldManager) {
//
//		FileManager fileManager = new FileManager(fileName);
//		String[] fileContent = null;
//		
//				
//		while ((fileContent = fileManager.ReadLine()) != null) {
//			try {
//				HashMap<String, String> rowMap = rowInputFieldManager.Process(fileContent);
//				sessionManager.Push(rowMap);
//
//			} catch (InvalidFormatException e) {
//				e.printStackTrace();
//			}
//			
//		}
//		
//	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println("Start Loading Meta Structure Configuration");
			MetaStructureManager.Load();
			SequenceManager sequenceManager = new SequenceManager();
			System.out.println("Initiating SequenceGenerators");
			sequenceManager.Load(MetaStructureManager.LoadSequences());
			System.out.println("SequenceGenerators successfull initialized");

			System.out.println("Initiating MetaNodes");
			MetaNodeManager metaNodesManager = new MetaNodeManager();
			metaNodesManager.Load(MetaStructureManager.LoadElement());
			//metaNodesManager.Traverse();
			System.out.println("MetaNodes successfull initialized");

			System.out.println("Reading "+args[0]+ "File");
			UnisonConfigurationManager configReader = new UnisonConfigurationManager(args[0]);
			SessionManager sessionManager = new SessionManager(sequenceManager, configReader);
			String outFileName = args[1];

			System.out.println("Initiating SQL Generation Process ");
			metaNodesManager.Process(sessionManager, outFileName);
			System.out.println("SQL Generation Process executed Successfully");

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
