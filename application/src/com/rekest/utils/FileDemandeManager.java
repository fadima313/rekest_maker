package com.rekest.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.rekest.entities.Demande;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FileDemandeManager {

	private static Workbook wb = new HSSFWorkbook();  
	private static Sheet sheetDemandes = wb.createSheet("Demandes"); 
	  
	
    public static void createWorkBook(File file) {
    	
        
        try(OutputStream fileOut = new FileOutputStream(file)) {  
            wb.write(fileOut);  
        }catch(Exception e) {  
            System.out.println(e.getMessage());  
            ErrorLogFileManager.appendError(e.getMessage());
        }  
    }
    
    public static void newCell(File file) {
    	
        try  (OutputStream os = new FileOutputStream(file)) {  
            
            Row row     = sheetDemandes.createRow(2);  
            Cell cell   = row.createCell(5);  
            cell.setCellValue("Javatpoint");  
            wb.write(os);  
        }catch(Exception e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    public static void setDemandes(File file,TableView<Demande> demandes) {
    	
        try  (OutputStream os = new FileOutputStream(file)) {  
            // header row
            Row header     = sheetDemandes.createRow(0);  
            
            // header style
            CellStyle headerStyle = wb.createCellStyle();  
            
            Font font= wb.createFont();
            font.setFontHeightInPoints((short)10);
            font.setFontName("Arial");
            
            headerStyle.setFont(font);
            header.setRowStyle(headerStyle);
            
            int cellCounter = 0;
            
            // set header
            for (TableColumn<Demande, ?> title : demandes.getColumns()) {
				
            	header.createCell(cellCounter).setCellValue(title.getText());
            	cellCounter++ ;
			}
            
            // set header
            cellCounter = 0;
            int rowCounter = 1;
            for (Demande obj : demandes.getItems()) {
            	Row row     = sheetDemandes.createRow(rowCounter);  
            	row.createCell(cellCounter).setCellValue(obj.getId());
            	cellCounter++ ;
			}
        
            wb.write(os);  
        }catch(Exception e) {  
            System.out.println(e.getMessage());  
        }  
    }
    

	
}
