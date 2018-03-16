package com.acc.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;

public class ClaimsUtility {
	public static void main(String args[]) throws Exception {
		/*
		 * File inputFile = new File("C:\\AISamplefiles\\Mocked_Data.xlsx");
		 * String xlsxFile = inputFile.getAbsolutePath();
		 */

		File outputFile = new File("C:\\Users\\renga.r.santh.ledge\\Documents\\test.csv");
		String csvFile = outputFile.getAbsolutePath();

		/*
		 * File outputFile = new
		 * File("C:\\AISamplefiles\\SampleData\\Mocked_Data_Test.csv"); String
		 * csvFile = outputFile.getAbsolutePath();
		 */

		// XLSX2CSV(xlsxFile, csvFile);

		/*
		 * File outputArffFile = new File("C:\\AISamplefiles\\Mocked_Data.csv");
		 * String arffFile = outputArffFile.getAbsolutePath();
		 */

		/*
		 * File outputCSVFile = new File("C:\\AISamplefiles\\Mocked_Data.csv");
		 * String csvFile1 = outputCSVFile.getAbsolutePath();
		 */

		File inputArffFile = new File("C:\\Users\\renga.r.santh.ledge\\Documents\\Mocked_Data_New.arff");
		String arffFile = inputArffFile.getAbsolutePath();

		/*
		 * File inputArffFile = new
		 * File("C:\\AISamplefiles\\SampleData\\Mocked_Data_Test.arff"); String
		 * arffFile = inputArffFile.getAbsolutePath();
		 */

		CSV2ARFF(csvFile, arffFile);

		// ARFF2CSV(csvFile1, arffFile);
	}

	/**
	 * takes 2 arguments: - XLSX input file - CSV output file
	 */
	public static byte[] XLSX2CSV(InputStream inputStream,String language) throws IOException {
		// For storing data into CSV files
		StringBuffer cellValue = new StringBuffer();
		byte[] csvData = null;
		try {
			// FileOutputStream fos = new FileOutputStream(csvFile);

			// Get the workbook instance for XLSX file
			XSSFWorkbook wb = new XSSFWorkbook(inputStream);

			// Get first sheet from the workbook
			XSSFSheet sheet = wb.getSheetAt(0);

			Row row;
			Cell cell;
			row = sheet.getRow(0);
			int ColumnCount = row.getLastCellNum();
			int rowCount = sheet.getLastRowNum();

			for (int rownum = 0; rownum <= rowCount; rownum++) {
				row = sheet.getRow(rownum);
				for (int colnum = 0; colnum < ColumnCount; colnum++) {
					cell = row.getCell(colnum);
					if (cell == null && language.equals("java")) {
						cellValue.append("?" + ",");
					}
					if (cell == null && language.equals("python")) {
						cellValue.append("" + ",");
					}

					else {
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_BOOLEAN:
							cellValue.append(cell.getBooleanCellValue() + ",");
							break;

						case Cell.CELL_TYPE_NUMERIC:
							cellValue.append(cell.getNumericCellValue() + ",");
							break;

						case Cell.CELL_TYPE_STRING:
							if (cell.getStringCellValue().equalsIgnoreCase("null") && language.equals("java"))
								cellValue.append("?" + ",");
							else if(cell.getStringCellValue().equalsIgnoreCase("null") && language.equals("python"))
								cellValue.append("" + ",");
							else
								cellValue.append(cell.getStringCellValue() + ",");
							break;

						case Cell.CELL_TYPE_BLANK:
							if(language.equals("java"))
								cellValue.append("?" + ",");
							if(language.equals("python"))
								cellValue.append("" + ",");
							break;

						default:
							cellValue.append(cell + ",");

						}

					}

				}
				cellValue.setLength(cellValue.length() - 1);
				cellValue.append("\n");
			}

			csvData = cellValue.toString().getBytes();

		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		}
		return csvData;
	}

	/**
	 * takes 2 arguments: - XLS input file - CSV output file
	 */
	public static byte[] XLS2CSV(InputStream inputStream,String language) throws IOException {
		// For storing data into CSV files
		StringBuffer cellDData = new StringBuffer();
		byte[] csvData = null;
		try {
			// Get the workbook instance for XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			// Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheetAt(0);
			Cell cell;
			Row row;
			row = sheet.getRow(0);
			int ColumnCount = row.getLastCellNum();
			int rowCount = sheet.getLastRowNum();

			for (int rownum = 0; rownum <= rowCount; rownum++) {
				row = sheet.getRow(rownum);
				for (int colnum = 0; colnum < ColumnCount; colnum++) {
					cell = row.getCell(colnum);
					if (cell == null) {
						cellDData.append(" " + ",");
					}

					else {
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_BOOLEAN:
							cellDData.append(cell.getBooleanCellValue() + ",");
							break;

						case Cell.CELL_TYPE_NUMERIC:
							cellDData.append(cell.getNumericCellValue() + ",");
							break;

						case Cell.CELL_TYPE_STRING:
							if (cell.getStringCellValue().equalsIgnoreCase("null") && language.equals("java"))
								cellDData.append("?" + ",");
							else if(cell.getStringCellValue().equalsIgnoreCase("null") && language.equals("python"))
								cellDData.append("" + ",");
							break;

						case Cell.CELL_TYPE_BLANK:
							if(language.equals("java"))
								cellDData.append("?" + ",");
							if(language.equals("python"))
								cellDData.append("" + ",");
							break;

						default:
							cellDData.append(cell + ",");

						}

					}

				}
				cellDData.setLength(cellDData.length() - 1);
				cellDData.append("\n");
			}
			csvData = cellDData.toString().getBytes();

		}

		catch (FileNotFoundException e) {
			System.err.println("Exception" + e.getMessage());
		} catch (IOException e) {
			System.err.println("Exception" + e.getMessage());
		}
		return csvData;
	}

	/**
	 * takes 2 arguments: - CSV input file - ARFF output file
	 */
	public static void CSV2ARFF(String csvFile, String arffFile) throws IOException {
		// Load CSV file
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(csvFile));
		// Get instance object
		Instances data = loader.getDataSet();

		// Save ARFF
		ArffSaver saver = new ArffSaver();
		// Set the dataset we want to convert
		saver.setInstances(data);
		// Save as ARFF
		saver.setFile(new File(arffFile));
		saver.writeBatch();
	}

	public static byte[] CSV2ARFF(InputStream inputStream) throws IOException {
		CSVLoader loader = new CSVLoader();
		loader.setSource(inputStream);
		Instances data = loader.getDataSet();
		byte[] arffContent = data.toString().getBytes();
		return arffContent;

	}

	/**
	 * takes 2 arguments: - ARFF input file - CSV output file
	 */
	public static void ARFF2CSV(String csvFile, String arffFile) throws IOException {
		// Load ARFF
		ArffLoader loader = new ArffLoader();
		loader.setSource(new File(arffFile));
		// Get instance object
		Instances data = loader.getDataSet();

		// Save CSV
		CSVSaver saver = new CSVSaver();
		// Set the dataset we want to convert
		saver.setInstances(data);
		// Save as CSV
		saver.setFile(new File(csvFile));
		saver.writeBatch();
	}

	public static byte[] ARFF2Model(InputStream inputStream) throws Exception {
		ArffLoader loader = new ArffLoader();
		loader.setSource(inputStream);
		Instances structure = loader.getDataSet();
		structure.setClassIndex(structure.numAttributes() - 1);
		Remove rm = new Remove();
		rm.setAttributeIndices("1"); 
		Classifier j48 = new J48();
		FilteredClassifier fc = new FilteredClassifier();
		fc.setFilter(rm);
		fc.setClassifier(j48);
		fc.buildClassifier(structure);
		
		String filePath = System.getProperty("java.io.tmpdir") + File.separator + "temp.model";	
		weka.core.SerializationHelper.write(filePath, fc);
		File file = new File(filePath);
		byte[] data = FileUtils.readFileToByteArray(file);
		//return fc.toString().getBytes();
		return data;
	}

}