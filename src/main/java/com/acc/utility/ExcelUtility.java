package com.acc.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpRequest;

import com.acc.dto.ExcelFile;

public class ExcelUtility {

	public static byte[] xlsxDataAppend(InputStream sourceFile, InputStream destFile) {
		byte[] resultExcelContent = null;
		
		try {

			XSSFWorkbook sourceWorkBook = new XSSFWorkbook(sourceFile);
			XSSFSheet sourceSheet = sourceWorkBook.getSheetAt(0);

			XSSFWorkbook destWorkBook = new XSSFWorkbook(destFile);
			XSSFSheet destSheet = destWorkBook.getSheetAt(0);

			Row sourceRow, destRow;
			sourceRow = sourceSheet.getRow(0);
			destRow = destSheet.getRow(0);

			int sourceRowCount = sourceSheet.getLastRowNum() + 1;
			int destColCount = destRow.getLastCellNum();
			int destRowCount = destSheet.getLastRowNum() + 1;

			for (int rowNum = destRowCount, srcCount = 1; rowNum < sourceRowCount
					+ destRowCount; rowNum++, srcCount++) {
				if (srcCount == sourceRowCount) {
					break;
				}
				sourceRow = sourceSheet.getRow(srcCount);
				XSSFRow newRowData = destSheet.createRow(rowNum);
				for (int colnum = 0; colnum < destColCount; colnum++) {
					if (sourceRow.getCell(colnum) == null) {
						newRowData.createCell(colnum).setCellValue("");
					}

					else {
						switch (sourceRow.getCell(colnum).getCellType()) {

						case Cell.CELL_TYPE_BOOLEAN:
							newRowData.createCell(colnum).setCellValue(sourceRow.getCell(colnum).getBooleanCellValue());
							break;

						case Cell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(sourceRow.getCell(colnum)))
								newRowData.createCell(colnum)
										.setCellValue(sourceRow.getCell(colnum).getDateCellValue());
							else
								newRowData.createCell(colnum)
										.setCellValue(sourceRow.getCell(colnum).getNumericCellValue());
							break;

						case Cell.CELL_TYPE_STRING:
							if (sourceRow.getCell(colnum).getStringCellValue().equalsIgnoreCase("null"))
								newRowData.createCell(colnum).setCellValue("NULL");
							else
								newRowData.createCell(colnum)
										.setCellValue(sourceRow.getCell(colnum).getStringCellValue());
							break;

						case Cell.CELL_TYPE_BLANK:
							newRowData.createCell(colnum).setCellValue("");
							break;

						case Cell.CELL_TYPE_ERROR:
							if (DateUtil.isCellDateFormatted(sourceRow.getCell(colnum)))
								newRowData.createCell(colnum)
										.setCellValue(sourceRow.getCell(colnum).getDateCellValue());
							break;

						}
					}
				}
			}

			String filePath = System.getProperty("java.io.tmpdir") + File.separator + "tempFile.xlsx";
			FileOutputStream stream = new FileOutputStream(filePath);
			destWorkBook.write(stream);
			stream.close();

			File file = new File(filePath);
			resultExcelContent = new byte[(int) file.length()];
			resultExcelContent = FileUtils.readFileToByteArray(file);
			file.delete();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultExcelContent;
	}

	public static byte[] xlsDataAppend(InputStream sourceFile, InputStream destFile) {
		byte[] resultExcelContent = null;
		try {
			HSSFWorkbook sourceWorkBook = new HSSFWorkbook(sourceFile);
			HSSFSheet sourceSheet = sourceWorkBook.getSheetAt(0);

			HSSFWorkbook destWorkBook = new HSSFWorkbook(destFile);
			HSSFSheet destSheet = destWorkBook.getSheetAt(0);

			Row sourceRow, destRow;
			sourceRow = sourceSheet.getRow(0);
			destRow = destSheet.getRow(0);

			int sourceRowCount = sourceSheet.getLastRowNum() + 1;
			int destColCount = destRow.getLastCellNum();
			int destRowCount = destSheet.getLastRowNum() + 1;

			for (int rowNum = destRowCount, srcCount = 0; rowNum < sourceRowCount
					+ destRowCount; rowNum++, srcCount++) {
				sourceRow = sourceSheet.getRow(srcCount);
				HSSFRow newRowData = destSheet.createRow(rowNum);
				for (int colnum = 0; colnum < destColCount; colnum++) {
					if (sourceRow.getCell(colnum) == null) {
						newRowData.createCell(colnum).setCellValue("");
					}

					else {
						switch (sourceRow.getCell(colnum).getCellType()) {

						case Cell.CELL_TYPE_BOOLEAN:
							newRowData.createCell(colnum).setCellValue(sourceRow.getCell(colnum).getBooleanCellValue());
							break;

						case Cell.CELL_TYPE_NUMERIC:
							newRowData.createCell(colnum).setCellValue(sourceRow.getCell(colnum).getNumericCellValue());
							break;

						case Cell.CELL_TYPE_STRING:
							if (sourceRow.getCell(colnum).getStringCellValue().equalsIgnoreCase("null"))
								newRowData.createCell(colnum).setCellValue("");
							else
								newRowData.createCell(colnum)
										.setCellValue(sourceRow.getCell(colnum).getStringCellValue());
							break;

						case Cell.CELL_TYPE_BLANK:
							newRowData.createCell(colnum).setCellValue("");
							break;

						}
					}
				}
			}

			String filePath = System.getProperty("java.io.tmpdir") + File.separator + "tempFile.xls";
			FileOutputStream stream = new FileOutputStream(filePath);
			destWorkBook.write(stream);
			stream.close();

			File file = new File(filePath);
			resultExcelContent = new byte[(int) file.length()];
			resultExcelContent = FileUtils.readFileToByteArray(file);
			file.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultExcelContent;
	}

	public static byte[] xlsxUnmerge(InputStream sourceFile) {
		byte[] resultExcelContent = null;
		try {

			XSSFWorkbook sourceWorkBook = new XSSFWorkbook(sourceFile);
			XSSFSheet sourceSheet = sourceWorkBook.getSheetAt(0);
			int nbrMergedRegions = sourceSheet.getNumMergedRegions();

			for (int i = 0; i < nbrMergedRegions; i++) {
				CellRangeAddress mergedRegion = sourceSheet.getMergedRegion(0);

				int firstrow = mergedRegion.getFirstRow();
				int lastrow = mergedRegion.getLastRow();
				int firstcol = mergedRegion.getFirstColumn();
				int lastcol = mergedRegion.getLastColumn();
				Cell cell = sourceSheet.getRow(firstrow).getCell(firstcol);
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					double value = sourceSheet.getRow(firstrow).getCell(firstcol).getNumericCellValue();
					sourceSheet.removeMergedRegion(0);
					for (int j = firstrow; j <= lastrow; j++) {
						for (int k = firstcol; k <= lastcol; k++) {
							sourceSheet.getRow(j).getCell(k).setCellValue(value);
						}
					}

					break;

				case Cell.CELL_TYPE_STRING:
					String value2 = sourceSheet.getRow(firstrow).getCell(firstcol).getStringCellValue();
					sourceSheet.removeMergedRegion(i);
					for (int j = firstrow; j <= lastrow; j++) {
						for (int k = firstcol; k <= lastcol; k++) {
							sourceSheet.getRow(j).getCell(k).setCellValue(value2);
						}
					}

					break;

				}

				// double value =
				// sourceSheet.getRow(firstrow).getCell(firstcol).getNumericCellValue();

			}

			String filePath = System.getProperty("java.io.tmpdir") + File.separator + "temFile.xlsx";
			FileOutputStream stream = new FileOutputStream(filePath);
			sourceWorkBook.write(stream);
			stream.close();

			File file = new File(filePath);
			resultExcelContent = new byte[(int) file.length()];
			resultExcelContent = FileUtils.readFileToByteArray(file);
			file.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultExcelContent;

	}

	public static byte[] finalColAppend(ExcelFile excelFile, List<String> predictionResult, XSSFWorkbook workBook) {
		byte[] resultExcelContent = null;
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(excelFile.getFileContent()));
			XSSFSheet sheet = wb.getSheetAt(0);

			XSSFSheet predictionSheet = workBook.createSheet("Prediction Result");
			Row row;
			Cell cell;
			row = sheet.getRow(0);
			int columnCount = row.getLastCellNum();
			int rowCount = sheet.getLastRowNum();

			for (int rownum = 0; rownum <= rowCount; rownum++) {
				XSSFRow predictionRow = predictionSheet.createRow(rownum);
				row = sheet.getRow(rownum);
				for (int colnum = 0; colnum < columnCount; colnum++) {
					cell = row.getCell(colnum);
					if (cell == null) {
						predictionRow.createCell(colnum).setCellValue("");
					} else {
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_BOOLEAN:
							predictionRow.createCell(colnum).setCellValue(cell.getBooleanCellValue());
							break;

						case Cell.CELL_TYPE_NUMERIC:
							predictionRow.createCell(colnum).setCellValue(cell.getNumericCellValue());
							break;

						case Cell.CELL_TYPE_STRING:
							predictionRow.createCell(colnum).setCellValue(cell.getStringCellValue());
							break;

						case Cell.CELL_TYPE_BLANK:
							predictionRow.createCell(colnum).setCellValue("");
							break;

						default:
							predictionRow.createCell(colnum).setCellValue("");
							break;

						}

					}

				}

			}
			XSSFRow predictionHeader = predictionSheet.getRow(0);
			predictionHeader.createCell(columnCount - 1).setCellValue("Claim Status");
			int rownum = 1;
			for (String predResult : predictionResult) {
				XSSFRow predictionRow = predictionSheet.getRow(rownum);
				predictionRow.createCell(columnCount - 1).setCellValue(predResult);
				rownum++;
			}

			String filePath = System.getProperty("java.io.tmpdir") + File.separator + "tempFile.xlsx";
			FileOutputStream stream = new FileOutputStream(filePath);
			workBook.write(stream);
			stream.close();

			File file = new File(filePath);
			resultExcelContent = new byte[(int) file.length()];
			resultExcelContent = FileUtils.readFileToByteArray(file);
			// file.delete();

		} catch (Exception e) {

		}

		return resultExcelContent;

	}
	
	public static XSSFWorkbook finalColAppend(XSSFWorkbook reportFinal,String[] penddata,String[] rejectdata,int claimsCount) {
	
		XSSFWorkbook wb = new XSSFWorkbook();
		try {
			
			XSSFSheet sheet = reportFinal.getSheetAt(0);
			XSSFSheet predictionSheet = wb.createSheet("Prediction Result");
			Row row;
			Cell cell;
			row = sheet.getRow(0);
			int columnCount = row.getLastCellNum();
			int rowCount = sheet.getLastRowNum();

			for (int rownum = 0; rownum <= rowCount; rownum++) {
				XSSFRow predictionRow = predictionSheet.createRow(rownum);
				row = sheet.getRow(rownum);
				for (int colnum = 0; colnum < columnCount; colnum++) {
					cell = row.getCell(colnum);
					if (cell == null) {
						predictionRow.createCell(colnum).setCellValue("");
					} else {
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_BOOLEAN:
							predictionRow.createCell(colnum).setCellValue(cell.getBooleanCellValue());
							break;

						case Cell.CELL_TYPE_NUMERIC:
							predictionRow.createCell(colnum).setCellValue(cell.getNumericCellValue());
							break;

						case Cell.CELL_TYPE_STRING:
							predictionRow.createCell(colnum).setCellValue(cell.getStringCellValue());
							break;

						case Cell.CELL_TYPE_BLANK:
							predictionRow.createCell(colnum).setCellValue("");
							break;

						default:
							predictionRow.createCell(colnum).setCellValue("");
							break;

						}

					}

				}

			}
			XSSFRow predictionHeader = predictionSheet.getRow(0);
			predictionHeader.createCell(columnCount).setCellValue("Reason Code");
			int pendnum=0,rejectnum=0;
			for (int rownum = 1;rownum<=claimsCount;rownum++) {
				XSSFRow predictionRow = predictionSheet.getRow(rownum);
				
				if(predictionRow.getCell(columnCount - 1).getStringCellValue().equalsIgnoreCase("accept")){
					predictionRow.createCell(columnCount).setCellValue("NA");
				}
				else if(predictionRow.getCell(columnCount - 1).getStringCellValue().equalsIgnoreCase("pend")){
					predictionRow.createCell(columnCount).setCellValue(penddata[pendnum]);
					pendnum++;
				}
				else if(predictionRow.getCell(columnCount - 1).getStringCellValue().equalsIgnoreCase("reject")){
					predictionRow.createCell(columnCount).setCellValue(rejectdata[rejectnum]);
					rejectnum++;;
				}
			
			}

			/*String filePath = System.getProperty("java.io.tmpdir") + File.separator + "tempFile.xlsx";
			FileOutputStream stream = new FileOutputStream(filePath);
			wb.write(stream);
			stream.close();*/
/*
			File file = new File(filePath);
			resultExcelContent = new byte[(int) file.length()];
			resultExcelContent = FileUtils.readFileToByteArray(file);
			// file.delete();
*/
		} catch (Exception e) {

		}

		return wb;

	}
	
	
	public static List<byte[]> splitExcel(XSSFWorkbook workbook ,HttpServletRequest request)
	{

		XSSFSheet sheet = workbook.getSheetAt(0);
		Workbook rejectworkBook = new XSSFWorkbook(); 
		Workbook pendworkBook = new XSSFWorkbook(); 
		
		XSSFSheet rejectsheet = (XSSFSheet) rejectworkBook.createSheet("reasoncode");
		XSSFSheet pendsheet = (XSSFSheet) pendworkBook.createSheet("reasoncode");
		
		List<byte[]> list = new ArrayList<byte[]>();
		int rejectrowcount = 0;
		int pendrowcount=0;
		int acceptrowcount =0;
		Row row;
		Cell cell;
		row = sheet.getRow(0);
		int columnCount = row.getLastCellNum();
		int rowCount = sheet.getLastRowNum();
		XSSFRow rejectRow = null;
		XSSFRow pendRow = null;
		XSSFRow acceptRow = null;
		
		rejectRow = rejectsheet.createRow(rejectrowcount++);
		pendRow = pendsheet.createRow(pendrowcount++);
		for (int colnum = 0; colnum < 13; colnum++) {
			cell = row.getCell(colnum);
			rejectRow.createCell(colnum).setCellValue(cell.getStringCellValue());
			pendRow.createCell(colnum).setCellValue(cell.getStringCellValue());
			
		}
		
		rejectRow.createCell(13).setCellValue("Reason Code");
		pendRow.createCell(13).setCellValue("Reason Code");
		
		
		for (int rownum = 1; rownum <= rowCount; rownum++) {
			
			row = sheet.getRow(rownum);
			cell = row.getCell(columnCount-1);
			System.out.println(cell.getStringCellValue());
			
			
			if(cell.getStringCellValue().equalsIgnoreCase("accept")) {
				continue;
				}
			else if(cell.getStringCellValue().equalsIgnoreCase("reject"))
			{
				rejectRow = rejectsheet.createRow(rejectrowcount++);
				for (int colnum = 0; colnum < 13; colnum++) {
					cell = row.getCell(colnum);
					if (cell == null) {
						rejectRow.createCell(colnum).setCellValue("");
					} else {
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_BOOLEAN:
							rejectRow.createCell(colnum).setCellValue(cell.getBooleanCellValue());
							break;

						case Cell.CELL_TYPE_NUMERIC:
							rejectRow.createCell(colnum).setCellValue(cell.getNumericCellValue());
							break;

						case Cell.CELL_TYPE_STRING:
							rejectRow.createCell(colnum).setCellValue(cell.getStringCellValue());
							break;

						case Cell.CELL_TYPE_BLANK:
							rejectRow.createCell(colnum).setCellValue("");
							break;

						default:
							rejectRow.createCell(colnum).setCellValue("");
							break;

						}

					}

				}
			}
			else if(cell.getStringCellValue().equalsIgnoreCase("pend"))
			{
				pendRow = pendsheet.createRow(pendrowcount++);
				for (int colnum = 0; colnum < 13; colnum++) {
					cell = row.getCell(colnum);
					if (cell == null) {
						pendRow.createCell(colnum).setCellValue("");
					} else {
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_BOOLEAN:
							pendRow.createCell(colnum).setCellValue(cell.getBooleanCellValue());
							break;

						case Cell.CELL_TYPE_NUMERIC:
							pendRow.createCell(colnum).setCellValue(cell.getNumericCellValue());
							break;

						case Cell.CELL_TYPE_STRING:
							pendRow.createCell(colnum).setCellValue(cell.getStringCellValue());
							break;

						case Cell.CELL_TYPE_BLANK:
							pendRow.createCell(colnum).setCellValue("");
							break;

						default:
							pendRow.createCell(colnum).setCellValue("");
							break;

						}

					}

				}
			}
		
		}
		
	
		byte[] pendarray = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			pendworkBook.write(bos);
			pendarray = bos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list.add(pendarray);
		byte[] rejectArray = null;
		ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
		try {
			rejectworkBook.write(bos1);
			rejectArray = bos1.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list.add(rejectArray);
	

		
		return list;
	}

}
