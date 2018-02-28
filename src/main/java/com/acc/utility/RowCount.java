package com.acc.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RowCount {
	
	public static int xlsRowCount(InputStream inputStream) throws IOException{
		int rowCount = 0;
		HSSFWorkbook  workbook = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = workbook.getSheetAt(0);
		rowCount = sheet.getLastRowNum();
		return rowCount;
	}
	
	public static int xlsxRowCount(InputStream inputStream) throws IOException{
		int rowCount = 0;
		XSSFWorkbook  workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		rowCount = sheet.getLastRowNum();
		return rowCount;
	}
	
	public static int csvRowCount(String filePath) throws IOException{
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
	     String input;
	     int count = 0;
	     while((input = bufferedReader.readLine()) != null)
	     {
	         count++;
	     }

		return count;
	}

}
