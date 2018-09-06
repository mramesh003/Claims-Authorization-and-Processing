package com.acc.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ColumnCount {
	public static Integer xlsColumnCount(Workbook workbook) throws IOException {
		int columnCount = 0;
		//HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
		HSSFRow row = sheet.getRow(0);
		if(row != null)
			columnCount = row.getLastCellNum();		
		return columnCount;
	}

	public static Integer xlsxColumnCount(Workbook workbook) throws IOException {
		int columnCount = 0;
		//XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
		XSSFRow row = sheet.getRow(0);
		if(row != null)
			columnCount = row.getLastCellNum();
		return columnCount;
	}

	public static Integer csvColumnCount(String filePath) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		String input;
		input = bufferedReader.readLine();
		String colcount[] = input.split(",");
		return colcount.length;
	}
}
