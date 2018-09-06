package com.acc.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.NestedServletException;

import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;
import com.acc.entity.FileUpload;
import com.acc.service.PrepareTrainDataService;
import com.acc.utility.ClaimsUtility;
import com.acc.utility.ColumnCount;
import com.acc.utility.ExcelUtility;
import com.acc.utility.RowCount;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
public class PrepareTrainDataController {

	@Autowired
	PrepareTrainDataService prepareTrainDataService;

	static Logger log = Logger.getLogger(PrepareTrainDataController.class.getName());

	@RequestMapping("prepareTrainData.htm")
	public ModelAndView trainSaveModel(HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ModelAndView modelandview = new ModelAndView();
		List<ExcelFile> excelFiles = prepareTrainDataService.listAllExcels();
		modelandview.addObject("excelFiles", excelFiles);
		modelandview.setViewName("prepareTrainingData");
		return modelandview;
	}

	@RequestMapping("uploadExcel.htm")
	public ModelAndView uploadExcel(HttpServletRequest request, FileUpload uploadItem) throws NestedServletException,NoClassDefFoundError,  Exception {
		ModelAndView modelandview = new ModelAndView();
		InputStream inputStream = null;
		InputStream inputStream1 = null;
		InputStream inputStream2 = null;
		String modelpe = "";




		try
		{
			String fileName[] = {"Main model.xlsx", "Pend model.xlsx", "Reject model.xlsx"}; 
			List<MultipartFile> files = uploadItem.getFile();
			for (MultipartFile file : files) {
				System.err.println("----------------------" +file.getOriginalFilename());
			}
			for (MultipartFile file : files) {
				//String fileName = file.getOriginalFilename();

				inputStream = file.getInputStream();
				inputStream1 = file.getInputStream();
				inputStream2 = file.getInputStream();
				InputStream inputStream6=null;
				XSSFWorkbook sourceWorkBook = new XSSFWorkbook(file.getInputStream());
				XSSFSheet sourceSheet = sourceWorkBook.getSheetAt(0);
				Row row = sourceSheet.getRow(0);
				int lastcolumnnum = row.getLastCellNum();
				String columnvalue=	row.getCell(lastcolumnnum-1).getStringCellValue();
				/*if(columnvalue.equalsIgnoreCase("Claim Status")) {
					modelType="General";

				}
				else if(columnvalue.equalsIgnoreCase("Pend Reason Code")) {
					modelType="Pend";
				}
				else if(columnvalue.equalsIgnoreCase("Reject Reason Code")) {
					modelType="Reject";
				}*/
				int nbrMergedRegions = sourceSheet.getNumMergedRegions();
				if(nbrMergedRegions >0){
					byte[] unmergedExcel = ExcelUtility.xlsxUnmerge(file.getInputStream());
					inputStream6 = new ByteArrayInputStream(unmergedExcel);
				}

				//splitting the main file
				List<Workbook> split = ExcelUtility.modelSplitExcel(sourceWorkBook);
				//for(int i = 0; i <= split.size(); i++) {
				int i=0;	
				for(Workbook splitexcel : split) {
					String fileNameForExcel = fileName[i];
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					splitexcel.write(bos);
					byte[] bytearray1 = bos.toByteArray();
					ByteArrayInputStream bis = new ByteArrayInputStream(bytearray1);



					ExcelFile excelFile = new ExcelFile();
					int position = fileNameForExcel.lastIndexOf(".");
					String fileType = fileNameForExcel.substring(position);
					ExcelFile dupeExcel = prepareTrainDataService.getExcelFileByName(fileNameForExcel);
					if (dupeExcel != null && dupeExcel.getFileContent() != null) {
						if (".xlsx".equals(fileType)) {
							ExcelFile appendFile = new ExcelFile();
							byte[] appendContent = ExcelUtility.xlsxDataAppend(bis,
									new ByteArrayInputStream(dupeExcel.getFileContent()));
							ByteArrayInputStream bis1 = new ByteArrayInputStream(appendContent);
							XSSFWorkbook appendBook = new XSSFWorkbook(bis1);
							appendFile.setFileName(fileNameForExcel);
							appendFile.setFileContent(appendContent);
							appendFile.setRowcount(RowCount.xlsxRowCount(appendBook));
							appendFile.setColCount(dupeExcel.getColCount());
							appendFile.setActiveStatus(true);
							if(fileNameForExcel.equalsIgnoreCase("Main model.xlsx")) {
								appendFile.setModeltype("general");
							}
							else if(fileNameForExcel.equalsIgnoreCase("Pend model.xlsx")) {
								appendFile.setModeltype("pend");
							}
							else if(fileNameForExcel.equalsIgnoreCase("Reject model.xlsx")) {
								appendFile.setModeltype("reject");
							}
							prepareTrainDataService.saveExcelFile(appendFile);
							prepareTrainDataService.updateExcelStatusToFalse(dupeExcel.getId());
						} else if (".xls".equals(fileType)) {
							ExcelFile appendFile = new ExcelFile();
							byte[] appendContent = ExcelUtility.xlsDataAppend(bis,
									new ByteArrayInputStream(dupeExcel.getFileContent()));
							ByteArrayInputStream bis1 = new ByteArrayInputStream(appendContent);
							HSSFWorkbook appendBook = new HSSFWorkbook(bis1);
							appendFile.setFileName(fileNameForExcel);
							appendFile.setFileContent(appendContent);
							appendFile.setRowcount(RowCount.xlsRowCount(appendBook));
							appendFile.setColCount(dupeExcel.getColCount());
							appendFile.setActiveStatus(true);
							if(fileNameForExcel.equalsIgnoreCase("Main model.xlsx")) {
								appendFile.setModeltype("general");
							}
							else if(fileNameForExcel.equalsIgnoreCase("Pend model.xlsx")) {
								appendFile.setModeltype("pend");
							}
							else if(fileNameForExcel.equalsIgnoreCase("Reject model.xlsx")) {
								appendFile.setModeltype("reject");
							}
							prepareTrainDataService.saveExcelFile(appendFile);
							prepareTrainDataService.updateExcelStatusToFalse(dupeExcel.getId());
						}
					} else {
						if (".xlsx".equals(fileType)) {
							excelFile.setRowcount(RowCount.xlsxRowCount(splitexcel)); 
							excelFile.setColCount(ColumnCount.xlsxColumnCount(splitexcel));

						} else if (".xls".equals(fileType)) {
							excelFile.setRowcount(RowCount.xlsRowCount(splitexcel));
							excelFile.setColCount(ColumnCount.xlsColumnCount(splitexcel));

						}
						if(inputStream6 == null){

							byte[] excelfileData = IOUtils.toByteArray(bis);
							excelFile.setFileName(fileNameForExcel);
							excelFile.setFileContent(excelfileData);
							excelFile.setActiveStatus(true);
							if(splitexcel.getSheetName(0).equalsIgnoreCase("acceptsheet")) {
								excelFile.setModeltype("general");
							}
							else if(splitexcel.getSheetName(0).equalsIgnoreCase("rejectsheet")) {
								excelFile.setModeltype("reject");
							}
							else if(splitexcel.getSheetName(0).equalsIgnoreCase("pendsheet")) {
								excelFile.setModeltype("pend");
							}

							prepareTrainDataService.saveExcelFile(excelFile);
						}
						else{
							byte[] excelfileData = bytearray1;
							excelFile.setFileName(fileNameForExcel);
							excelFile.setFileContent(bytearray1);
							excelFile.setActiveStatus(true);
							if(splitexcel.getSheetName(0).equalsIgnoreCase("acceptsheet")) {
								excelFile.setModeltype("general");
							}
							else if(splitexcel.getSheetName(0).equalsIgnoreCase("rejectsheet")) {
								excelFile.setModeltype("reject");
							}
							else if(splitexcel.getSheetName(0).equalsIgnoreCase("pendsheet")) {
								excelFile.setModeltype("pend");
							}
							prepareTrainDataService.saveExcelFile(excelFile);
						}

					}
					i++;

				}
				List<ExcelFile> excelFiles = prepareTrainDataService.listAllExcels();
				modelandview.addObject("excelFiles", excelFiles);
				modelandview.addObject("message", "successUpload");
				modelandview.setViewName("prepareTrainingData");
			}



		}
		catch(NestedServletException e)
		{
			modelandview.addObject("error", e.getMessage());
			modelandview.setViewName("errorPage");
		}
		catch(NoClassDefFoundError e)
		{
			modelandview.addObject("error", e.getMessage());
			modelandview.setViewName("errorPage");
		}
		catch(Exception e)
		{
			modelandview.addObject("error", e.getMessage());
			modelandview.setViewName("errorPage");
		}
		return modelandview;
	}

	@RequestMapping("downloadExcel.htm")
	public void downloadExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) throws Exception {
		ExcelFile excelFile = prepareTrainDataService.getExcelFileById(Integer.valueOf(id));

		ByteArrayInputStream in = new ByteArrayInputStream(excelFile.getFileContent());
		OutputStream outStream = response.getOutputStream();
		try
		{
			String fileName = URLEncoder.encode(excelFile.getFileName(), "UTF-8");
			fileName = URLDecoder.decode(fileName, "ISO8859_1");
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = in.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
		}
		catch(Exception e)
		{
			ModelAndView modelandview = new ModelAndView();
			modelandview.addObject("error", e.getMessage());
			modelandview.setViewName("errorPage");
		}
	}

	@RequestMapping(value={"convertToCsv.htm"},method = RequestMethod.POST)
	public String convertToCsv(HttpServletRequest request, @RequestParam("excelId") String id,@RequestParam("language") String language) throws Exception {
		ModelAndView modelandview = new ModelAndView();
		System.out.println(id);
		List<String> excelIds = new ArrayList<String>();
		ObjectMapper mapper1 = new ObjectMapper();
		String[] array = mapper1.readValue(id, String[].class);
		System.out.println(array);
		for(String ary : array) {
			excelIds.add(ary);
		}


		System.out.println(excelIds);

		try
		{
			for(String id1 :excelIds) {

				ExcelFile excelFile = prepareTrainDataService.getExcelFileById(Integer.valueOf(id1));

				int position = excelFile.getFileName().lastIndexOf(".");
				String fileType = excelFile.getFileName().substring(position);
				InputStream inputStream = new ByteArrayInputStream(excelFile.getFileContent());
				byte[] csvData = null;
				if (".xlsx".equals(fileType)) {
					csvData = ClaimsUtility.XLSX2CSV(inputStream,language);
				}
				else if (".xls".equals(fileType)) {
					csvData = ClaimsUtility.XLS2CSV(inputStream,language);
				}
				String csvName = excelFile.getFileName().substring(0, position) + ".csv";
				log.info("csvName : " + csvName);
				CsvFile csvFile = new CsvFile();
				csvFile.setFileName(csvName);
				csvFile.setFileContent(csvData);
				csvFile.setExcelId(excelFile.getId());
				csvFile.setRowCount(excelFile.getRowcount());
				csvFile.setColumnCount(excelFile.getColCount());
				csvFile.setModeltype(excelFile.getModeltype());
				if(language.equals("java"))
					csvFile.setIsJava(true);
				else
					csvFile.setIsJava(false);

				prepareTrainDataService.saveCsvFile(csvFile);
			}
		}
		catch(Exception e)
		{
			modelandview.addObject("error", e.getMessage());
			modelandview.setViewName("errorPage");
		}
		return "prepareTrainingData";
	}
	@RequestMapping("deleteExcel.htm")
	public ModelAndView deleteExcel(HttpServletRequest request, @RequestParam("var1") String id) throws Exception {
		ModelAndView modelandview = new ModelAndView();
		try
		{
			CsvFile csvFile = prepareTrainDataService.getCsvFileByExcelId(Integer.valueOf(id));
			ExcelFile excelFile = prepareTrainDataService.getExcelFileById(Integer.valueOf(id));
			if (csvFile.getExcelId() != null){
				modelandview.addObject("message", "CannotDeleteExcel");
			}	
			else{
				prepareTrainDataService.deleteExcel(excelFile);
				modelandview.addObject("message", "deletedSuccessfully");
			}
			List<ExcelFile> excelFiles = prepareTrainDataService.listAllExcels();
			modelandview.addObject("excelFiles", excelFiles);
			modelandview.setViewName("prepareTrainingData");
		}
		catch(Exception e)
		{
			modelandview.addObject("error", e.getMessage());
			modelandview.setViewName("errorPage");
		}
		return modelandview;
	}

}







