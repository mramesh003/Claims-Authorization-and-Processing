package com.acc.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;

public class ClaimsUtility {
	public static void main(String args[]) throws Exception{
		/*File inputFile = new File("C:\\AISamplefiles\\Mocked_Data.xlsx");
		String xlsxFile = inputFile.getAbsolutePath();*/
		
		File outputFile = new File("C:\\AISamplefiles\\SampleData\\Mocked_Data_Updated.csv");
		String csvFile = outputFile.getAbsolutePath();
		
		/*File outputFile = new File("C:\\AISamplefiles\\SampleData\\Mocked_Data_Test.csv");
		String csvFile = outputFile.getAbsolutePath();*/
		
		//XLSX2CSV(xlsxFile, csvFile);
		
		/*File outputArffFile = new File("C:\\AISamplefiles\\Mocked_Data.csv");
		String arffFile = outputArffFile.getAbsolutePath();*/
		
		/*File outputCSVFile = new File("C:\\AISamplefiles\\Mocked_Data.csv");
		String csvFile1 = outputCSVFile.getAbsolutePath();*/
		
		File inputArffFile = new File("C:\\AISamplefiles\\SampleData\\Mocked_Data_New.arff");
		String arffFile = inputArffFile.getAbsolutePath();
		
		/*File inputArffFile = new File("C:\\AISamplefiles\\SampleData\\Mocked_Data_Test.arff");
		String arffFile = inputArffFile.getAbsolutePath();*/
		
		CSV2ARFF(csvFile, arffFile);
		
		//ARFF2CSV(csvFile1, arffFile);
	}
	
	/**
	   * takes 2 arguments:
	   * - XLSX input file
	   * - CSV output file
	*/
	public static void XLSX2CSV(String xlsxFile, String csvFile) throws IOException{
		 // For storing data into CSV files
		StringBuffer cellValue = new StringBuffer();
		try 
		{
		        FileOutputStream fos = new FileOutputStream(csvFile);

		        // Get the workbook instance for XLSX file
		        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(xlsxFile));

		        // Get first sheet from the workbook
		        XSSFSheet sheet = wb.getSheetAt(0);

		        Row row;
		        Cell cell;

		        // Iterate through each rows from first sheet
		        Iterator<Row> rowIterator = sheet.iterator();

		        while (rowIterator.hasNext()) 
		        {
		        row = rowIterator.next();

		        // For each row, iterate through each columns
		        Iterator<Cell> cellIterator = row.cellIterator();
		        while (cellIterator.hasNext()) 
		        {
		                cell = cellIterator.next();

		                switch (cell.getCellType()) 
		                {
		                
		                case Cell.CELL_TYPE_BOOLEAN:
		                        cellValue.append(cell.getBooleanCellValue() + ",");
		                        break;
		                
		                case Cell.CELL_TYPE_NUMERIC:
		                        cellValue.append(cell.getNumericCellValue() + ",");
		                        break;
		                
		                case Cell.CELL_TYPE_STRING:
		                        cellValue.append(cell.getStringCellValue() + ",");
		                        break;

		                case Cell.CELL_TYPE_BLANK:
		                        cellValue.append("" + ",");
		                        break;
		                        
		                default:
		                        cellValue.append(cell + ",");

		                }
		        }
		        }

		fos.write(cellValue.toString().getBytes());
		fos.close();

		} 
		catch (Exception e) 
		{
		        System.err.println("Exception :" + e.getMessage());
		}
	}

	/**
	   * takes 2 arguments:
	   * - XLS input file
	   * - CSV output file
	*/
	public static void XLS2CSV(String xlsFile, String csvFile) throws IOException{
		// For storing data into CSV files
		StringBuffer cellDData = new StringBuffer();
		try 
		{
		        FileOutputStream fos = new FileOutputStream(csvFile);

		        // Get the workbook instance for XLS file
		        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(xlsFile));
		        // Get first sheet from the workbook
		        HSSFSheet sheet = workbook.getSheetAt(0);
		        Cell cell;
		        Row row;

		        // Iterate through each rows from first sheet
		        Iterator<Row> rowIterator = sheet.iterator();
		        while (rowIterator.hasNext()) 
		        {
		        row = rowIterator.next();

		        // For each row, iterate through each columns
		        Iterator<Cell> cellIterator = row.cellIterator();
		        while (cellIterator.hasNext()) 
		        {
		        cell = cellIterator.next();

		        switch (cell.getCellType()) 
		        {
		        
		        case Cell.CELL_TYPE_BOOLEAN:
		                cellDData.append(cell.getBooleanCellValue() + ",");
		                break;
		        
		        case Cell.CELL_TYPE_NUMERIC:
		                cellDData.append(cell.getNumericCellValue() + ",");
		                break;
		        
		        case Cell.CELL_TYPE_STRING:
		                cellDData.append(cell.getStringCellValue() + ",");
		                break;

		        case Cell.CELL_TYPE_BLANK:
		                cellDData.append("" + ",");
		                break;
		                
		        default:
		                cellDData.append(cell + ",");
		        }
		        }
		        }

		fos.write(cellDData.toString().getBytes());
		fos.close();

		}
		catch (FileNotFoundException e) 
		{
		    System.err.println("Exception" + e.getMessage());
		} 
		catch (IOException e) 
		{
		        System.err.println("Exception" + e.getMessage());
		}
	}
	
	/**
	   * takes 2 arguments:
	   * - CSV input file
	   * - ARFF output file
	*/
	public static void CSV2ARFF(String csvFile, String arffFile) throws IOException{
		//Load CSV file
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(csvFile));
		//Get instance object
		Instances data = loader.getDataSet();
		
		//Save ARFF
		ArffSaver saver = new ArffSaver();
		//Set the dataset we want to convert
		saver.setInstances(data);
		//Save as ARFF
		saver.setFile(new File(arffFile));
		saver.writeBatch();
	}
	
	/**
	   * takes 2 arguments:
	   * - ARFF input file
	   * - CSV output file
	*/
	public static void ARFF2CSV(String csvFile, String arffFile) throws IOException{
		//Load ARFF
		ArffLoader loader = new ArffLoader();
		loader.setSource(new File(arffFile));
		//Get instance object
		Instances data = loader.getDataSet();
		
		//Save CSV
		CSVSaver saver = new CSVSaver();
		//Set the dataset we want to convert
		saver.setInstances(data);
		//Save as CSV
		saver.setFile(new File(csvFile));
		saver.writeBatch();
	}
}