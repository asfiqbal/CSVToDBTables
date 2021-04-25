package com.avanza.unison.tools;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;  

public class FileManager {

	BufferedReader br = null;
	
	public FileManager(String fileNameWithPath) {
		Open(fileNameWithPath);
	}
	
	public void Open(String fileNameWithPath) {
		try	{
			br = new BufferedReader(new FileReader(fileNameWithPath));  
		}
		catch (IOException e)   
		{  
		e.printStackTrace();  
		}  
		
	}
	
	public String[] ReadLine() {
		String line = "";  
		String splitBy = ","; 
		String[] data = null;
		try {
		
			line = br.readLine();   //returns a Boolean value  
			if (line == null)
				return null;
			
			data = line.split(splitBy);    // use comma as separator  
		}
		catch (IOException e)   
		{  
			e.printStackTrace();  
		}  
		return data;
		
	}
	
	public static void WriteListToFile(List<String> sqlList, String filePath) {
		try {
			

			File file = new File(filePath);

			FileWriter writer = new FileWriter(file);

	        for (String record: sqlList) {
	            writer.write(record);
	            writer.write("\n");
	        }
	        writer.flush();
	        writer.close();

		}
		catch(Exception e) {
		}
	}


}
