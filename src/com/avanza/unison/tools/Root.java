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
	
	
	public static void ProcessInputFile(String fileName, SessionManager sessionManager, RowInputFieldManager rowInputFieldManager) {

		FileManager fileManager = new FileManager(fileName);
		String[] fileContent = null;
		
				
		while ((fileContent = fileManager.ReadLine()) != null) {
			try {
				HashMap<String, String> rowMap = rowInputFieldManager.Process(fileContent);
				sessionManager.Push(rowMap);

			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Start Loading Meta Structure Configuration");
		MetaStructureManager.Load();
		SequenceManager sequenceManager = new SequenceManager();
		System.out.println("Initiating SequenceGenerators");
		sequenceManager.Load(MetaStructureManager.LoadSequences());
		System.out.println("SequenceGenerators successfull initialized");
		RowInputFieldManager rowInputFieldManager = new RowInputFieldManager();
		System.out.println("Initializing Input fields");
		rowInputFieldManager.Load(MetaStructureManager.LoadInputFields());
		System.out.println("Input fields initialized Successfully");
		OutputStructureManager outStructManager = new OutputStructureManager();
		System.out.println("Loading Output Structure Configuration");
		outStructManager.Load(MetaStructureManager.LoadOutputStructure());
		System.out.println("Output Structure Configuration loaded Successfully");
		System.out.println("Meta Structure Configuration Loaded Successfully");
		
		SessionManager sessionManager = new SessionManager(sequenceManager);
		
		ProcessInputFile(args[0], sessionManager, rowInputFieldManager);
		
		outStructManager.Process(sessionManager, args[1]);

	}

}
