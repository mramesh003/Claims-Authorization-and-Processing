package com.acc.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;
import com.acc.entity.FileUpload;
import com.acc.service.PrepareTrainDataService;
import com.acc.utility.ClaimsUtility;

@Controller
public class PrepareTrainDataController {
	
	@Autowired
	PrepareTrainDataService prepareTrainDataService;
	
	 static Logger log = Logger.getLogger(PrepareTrainDataController.class.getName());
	 
	 @RequestMapping("prepareTrainData.htm")
     public ModelAndView trainSaveModel(HttpServletRequest request, HttpServletResponse response )
     {
                     ModelAndView modelandview = new ModelAndView();
                     modelandview.setViewName("prepareTrainingData");
                     return modelandview;
     }
	 
	 @RequestMapping("xlToCsv.htm")
	 public ModelAndView convertToArff(HttpServletRequest request, HttpServletResponse response, FileUpload uploadItem) throws IOException {
		 ModelAndView modelandview = new ModelAndView();
		 List<MultipartFile> files = uploadItem.getFile();
		 InputStream inputStream = null;
		 for(MultipartFile file : files)
		 {
			String fileName = file.getOriginalFilename();
			int position = fileName.lastIndexOf(".");
			String fileType = fileName.substring(position);
			inputStream = file.getInputStream();
			byte[] excelfileData = IOUtils.toByteArray(inputStream);

			ExcelFile excelFile = new ExcelFile();
			excelFile.setFileName(fileName);
			excelFile.setFileContent(excelfileData);
			prepareTrainDataService.saveExcelFile(excelFile);
			Integer excelId = excelFile.getId();
			
			inputStream = file.getInputStream();
			byte[] csvData = null;
			log.info("fileTyppe : " + fileType);
			if(".xlsx".equals(fileType)) {
				csvData = ClaimsUtility.XLSX2CSV(inputStream);
			}
			else if(".xls".equals(fileType)) {
				csvData = ClaimsUtility.XLS2CSV(inputStream);
			}
			String csvName = fileName.substring(0, position) + ".csv";
			log.info("csvName : " + csvName);
			CsvFile csvFile = new CsvFile();
			csvFile.setFileName(csvName);
			csvFile.setFileContent(csvData);
			csvFile.setExcelId(excelId);
			prepareTrainDataService.saveCsvFile(csvFile);
		 }
	
		 modelandview.setViewName("prepareTrainingData");
		 modelandview.addObject("message", "success");
         return modelandview;
	 }


}
