package com.acc.utility.excelBuilder;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.acc.dto.ExcelFile;

public class ModelEvalReport  extends AbstractExcelView  {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workBook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*ExcelFile excelFile  = (ExcelFile)model.get("excelFile");
     	List<String> predictionResult = (List<String>)model.get("results");
     	Map<String,Object> evaluationResult = (Map<String,Object>)model.get("evaluationResult");*/
     	HttpSession session = request.getSession();
     	ExcelFile excelFile = (ExcelFile)session.getAttribute("excelFile");
     	List<String> predictionResult = (List<String>)session.getAttribute("results");
     	Map<String,Object> evaluationResult = (Map<String,Object>)session.getAttribute("evaluationResult");
     	String fileName = excelFile.getFileName();
     	int position = fileName.lastIndexOf(".");
		String fileType = fileName.substring(position);
		
     	if (".xlsx".equals(fileType)) {
     		xlsxReport(excelFile,predictionResult,evaluationResult,workBook);
     	}
     	else if(".xls".equals(fileType)) {
     		xlsReport(excelFile,predictionResult,evaluationResult,workBook);
     	}
     	
	}

	private void xlsxReport(ExcelFile excelFile, List<String> predictionResult,Map<String,Object> evaluationResult, HSSFWorkbook workBook) {
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(excelFile.getFileContent()));
			XSSFSheet sheet = wb.getSheetAt(0);

			HSSFSheet predictionSheet = workBook.createSheet("Prediction Result");
			Row row;
			Cell cell;
			row = sheet.getRow(0);
			int columnCount = row.getLastCellNum();
			int rowCount = sheet.getLastRowNum();

			for (int rownum = 0; rownum <= rowCount; rownum++) {
				HSSFRow predictionRow = predictionSheet.createRow(rownum);
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
			HSSFRow predictionHeader = predictionSheet.getRow(0);
			predictionHeader.createCell(columnCount - 1).setCellValue("Predicted Result");
			int rownum = 1;
			for (String predResult : predictionResult) {
				HSSFRow predictionRow = predictionSheet.getRow(rownum);
				predictionRow.createCell(columnCount - 1).setCellValue(predResult);
				rownum++;
			}
			 
			HSSFSheet predictionSheet1 = workBook.createSheet("Evaluation Result");
			predictionSheet1.setDefaultColumnWidth(50);
			predictionSheet1.setDefaultRowHeight((short) 100);
			 HSSFRow header = predictionSheet1.createRow(1);
		     header.createCell(0).setCellValue("Summary String");
		     header.createCell(1).setCellValue("Confusion Matrix");
		     
		     HSSFRow result = predictionSheet1.createRow(2);
		     result.createCell(0).setCellValue(evaluationResult.get("Evaluation results").toString());
		     result.createCell(1).setCellValue(evaluationResult.get("Confusion Matrix").toString());
		
		} catch (Exception e) {

		}
	}

	private void xlsReport(ExcelFile excelFile, List<String> predictionResult,Map<String,Object> evaluationResult, HSSFWorkbook workBook) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new ByteArrayInputStream(excelFile.getFileContent()));
			HSSFSheet sheet = wb.getSheetAt(0);

			HSSFSheet predictionSheet = workBook.createSheet("Prediction Result");
			Row row;
			Cell cell;
			row = sheet.getRow(0);
			int columnCount = row.getLastCellNum();
			int rowCount = sheet.getLastRowNum();

			for (int rownum = 0; rownum <= rowCount; rownum++) {
				HSSFRow predictionRow = predictionSheet.createRow(rownum);
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
			HSSFRow predictionHeader = predictionSheet.getRow(0);
			predictionHeader.createCell(columnCount - 1).setCellValue("Predicted Result");
			int rownum = 1;
			for (String predResult : predictionResult) {
				HSSFRow predictionRow = predictionSheet.getRow(rownum);
				predictionRow.createCell(columnCount - 1).setCellValue(predResult);
				rownum++;
			}
			HSSFSheet predictionSheet1 = workBook.createSheet("Evaluation Result");
			predictionSheet1.setDefaultColumnWidth(50);
			predictionSheet1.setDefaultRowHeightInPoints(100000);
			 HSSFRow header = predictionSheet1.createRow(1);
		     header.createCell(0).setCellValue("Summary String");
		     header.createCell(1).setCellValue("Confusion Matrix");
		     
		     HSSFRow result = predictionSheet1.createRow(2);
		     result.createCell(0).setCellValue(evaluationResult.get("Evaluation results").toString());
		     result.createCell(1).setCellValue(evaluationResult.get("Confusion Matrix").toString());
		     
		     
			
		}catch(Exception e) {
			
		}
	}

}
