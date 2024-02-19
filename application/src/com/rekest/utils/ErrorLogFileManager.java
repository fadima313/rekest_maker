package com.rekest.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class ErrorLogFileManager {

	private static File errorLogFile;
	
	private static final String PATH_FILE = "errors.txt";
	
	public static boolean createErrorLogFile() {
	    try {
	    	errorLogFile = new File(PATH_FILE);
	        if (errorLogFile.createNewFile()) {
	          System.out.println(" Error log File created: " + errorLogFile.getName());
	        } else {
	          System.out.println(" Error log File already exists.");
	        }
	      } catch (IOException e) {
	        System.out.println("An error occurred while creating error log file .");
	        e.printStackTrace();
	        return false;
	      }
	    
	    return true;
	}
	
	public static boolean appendError(String line) {
		createErrorLogFile();
	    try {
	        FileWriter myWriter = new FileWriter(errorLogFile,true);
	        myWriter.append(LocalDateTime.now()+"  "+line+"\n");
	        myWriter.close();
	        System.out.println("Error successfully wrote to the file.");
	      } catch (IOException e) {
	        System.out.println("An error occurred while writing the error log file .");
	        e.printStackTrace();
	        return false;
	      }
	    return true;
	}
}
