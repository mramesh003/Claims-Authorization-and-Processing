package com.acc.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
				if(srcCount == sourceRowCount) {
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
								newRowData.createCell(colnum).setCellValue(sourceRow.getCell(colnum).getDateCellValue());
							else
								newRowData.createCell(colnum).setCellValue(sourceRow.getCell(colnum).getNumericCellValue());
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
								newRowData.createCell(colnum).setCellValue(sourceRow.getCell(colnum).getDateCellValue());
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

	public static byte[] xlsxUnmerge(InputStream sourceFile) {
		byte[] resultExcelContent = null;
		try {

			XSSFWorkbook sourceWorkBook = new XSSFWorkbook(sourceFile);
			XSSFSheet sourceSheet = sourceWorkBook.getSheetAt(0);
			int nbrMergedRegions = sourceSheet.getNumMergedRegions();

			System.out.println("found a total of merged regions of: "
					+ String.valueOf( nbrMergedRegions ));
			System.out.println("syso merged"+sourceSheet.getMergedRegion(1));
			for (int i = 0; i < nbrMergedRegions; i++)
			{
				CellRangeAddress mergedRegion = sourceSheet.getMergedRegion(0);
				System.out.println("Number of Merged Regions"+String.valueOf(mergedRegion));
				System.out.println("Merged Regions"+String.valueOf(mergedRegion.getFirstRow())+String.valueOf(mergedRegion.getLastRow())+String.valueOf(mergedRegion.getFirstColumn())+String.valueOf(mergedRegion.getLastColumn()));
				int firstrow = mergedRegion.getFirstRow();
				int lastrow = mergedRegion.getLastRow();
				int firstcol = mergedRegion.getFirstColumn();
				int lastcol = mergedRegion.getLastColumn();
				System.out.println("merged regions altered"+firstrow+lastrow+firstcol+lastcol);
				Cell cell = sourceSheet.getRow(firstrow).getCell(firstcol);
				switch(cell.getCellType())
				{	
				case Cell.CELL_TYPE_NUMERIC:
					double value = sourceSheet.getRow(firstrow).getCell(firstcol).getNumericCellValue();
					sourceSheet.removeMergedRegion(0);
					for(int j = firstrow;j <= lastrow;j++)
					{
						for(int k= firstcol; k <= lastcol;k++)
						{
							sourceSheet.getRow(j).getCell(k).setCellValue(value);
						}
					}

					break;

				case Cell.CELL_TYPE_STRING:
					String value2 = sourceSheet.getRow(firstrow).getCell(firstcol).getStringCellValue();
					sourceSheet.removeMergedRegion(i);
					for(int j = firstrow;j <= lastrow;j++)
					{
						for(int k= firstcol; k <= lastcol;k++)
						{
							sourceSheet.getRow(j).getCell(k).setCellValue(value2);
						}
					}

					break;

				}

				//double value = sourceSheet.getRow(firstrow).getCell(firstcol).getNumericCellValue();

			}

			String filePath = System.getProperty("java.io.tmpdir") + File.separator + "temFile.xlsx";
			FileOutputStream stream = new FileOutputStream(filePath);
			sourceWorkBook.write(stream);
			stream.close();

			File file = new File(filePath);
			resultExcelContent = new byte[(int) file.length()];
			resultExcelContent = FileUtils.readFileToByteArray(file);
			file.delete();



		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return resultExcelContent;

	}


}
