package com.avanza.unison.tools;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.avanza.unison.tools.objects.SQLCollection;  

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
	
	public static void WriteListToFile(List<SQLCollection> sqlList, String filePath) {
		try {
			

			File insertFile = new File(filePath);
			File deleteFile = new File(filePath+".del");

			FileWriter writer = new FileWriter(insertFile);
			FileWriter writerDeleteFile = new FileWriter(deleteFile);

	        for (SQLCollection record: sqlList) {
	            writer.write(record.getInsertSQL());
	            writer.write("\n");
	            writerDeleteFile.write(record.getDeleteSQL());
	            writerDeleteFile.write("\n");
	        }
	        writer.flush();
	        writer.close();
	        writerDeleteFile.flush();
	        writerDeleteFile.close();

		}
		catch(Exception e) {
		}
	}


}
