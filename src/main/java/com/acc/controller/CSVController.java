package com.acc.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;
import com.acc.entity.FileUpload;
import com.acc.service.PrepareTrainDataService;

@Controller
public class CSVController {
	@Autowired
	PrepareTrainDataService prepareTrainDataService;
	 @RequestMapping("uploadCsv.htm")
	 public ModelAndView uploadCsv(HttpServletRequest request, FileUpload uploadItem) throws IOException {
		 ModelAndView modelandview = new ModelAndView();
		 List<MultipartFile> files = uploadItem.getFile();
		 InputStream inputStream = null;
		 for(MultipartFile file : files)
		 {
			String fileName = file.getOriginalFilename();
			inputStream = file.getInputStream();
			byte[] csvfileData = IOUtils.toByteArray(inputStream);
			
			CsvFile csvFile = new CsvFile();
			csvFile.setFileName(fileName);
			csvFile.setFileContent(csvfileData);
			csvFile.setExcelId(null);			
			prepareTrainDataService.saveCsvFile(csvFile);
		 }
		 List<CsvFile> csvFiles = prepareTrainDataService.listAllCsvs();
         modelandview.addObject("csvFiles", csvFiles);
		 modelandview.addObject("message", "successUpload");
		 modelandview.setViewName("prepareTrainingModel");
         return modelandview;
	 }
	 
	 @RequestMapping("downloadCsv.htm")
	 public void downloadCsv(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) throws IOException {
		CsvFile csvFile = prepareTrainDataService.getCsvFileById(Integer.valueOf(id));

		ByteArrayInputStream in = new ByteArrayInputStream(csvFile.getFileContent());
		OutputStream outStream = response.getOutputStream();
		String fileName = URLEncoder.encode(csvFile.getFileName(), "UTF-8");
		fileName = URLDecoder.decode(fileName, "ISO8859_1");
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		while ((bytesRead = in.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
	 }
	 
	 @RequestMapping("deletecsv.htm")
	 public ModelAndView deleteCsv(HttpServletRequest request)
	 {
		 ModelAndView modelandview = new ModelAndView();
		 Integer id = Integer.valueOf(request.getParameter("id"));
		 CsvFile csvFile = prepareTrainDataService.getCsvFileById(id);
		 prepareTrainDataService.deleteCsv(csvFile);		 
		 modelandview.setViewName("prepareTrainingModel");
		 List<CsvFile> csvFiles = prepareTrainDataService.listAllCsvs();
         modelandview.addObject("csvFiles", csvFiles);
		 return modelandview;
	 }
	 
	 

}
