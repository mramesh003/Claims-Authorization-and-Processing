package com.acc.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
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

import com.acc.dto.ArffFile;
import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;
import com.acc.entity.FileUpload;
import com.acc.service.PrepareTrainDataService;
import com.acc.service.TrainModelService;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import org.apache.log4j.Logger;

@Controller
public class TrainModelController {
	
	@Autowired
	PrepareTrainDataService prepareTrainDataService;
	
	@Autowired
	TrainModelService trainModelService;

	 static Logger log = Logger.getLogger(TrainModelController.class.getName());
	 
	 @RequestMapping("prepareTrainingModel.htm")
     public ModelAndView testModel(HttpServletRequest request, HttpServletResponse response)
     {
                     ModelAndView modelandview = new ModelAndView();
                     List<CsvFile> csvFiles = prepareTrainDataService.listAllCsvs();
                     modelandview.addObject("csvFiles", csvFiles);
                     modelandview.setViewName("prepareTrainingModel");
                     return modelandview;
     }
	 
	 @RequestMapping("csvToArff.htm")
	 public ModelAndView convertToArff(HttpServletRequest request, @RequestParam("id") String id) throws IOException {
		 ModelAndView modelandview = new ModelAndView();
		 Boolean flag = trainModelService.getArffFilebyCsvId(Integer.valueOf(id));
		 List<CsvFile> csvFiles = prepareTrainDataService.listAllCsvs();
		 modelandview.addObject("flag", flag);
         modelandview.addObject("csvFiles", csvFiles);
		 modelandview.setViewName("prepareTrainingModel");
         return modelandview;
	 }
	 
	 @RequestMapping("uploadArff.htm")
		public ModelAndView uploadArff(HttpServletRequest request, FileUpload uploadItem) throws IOException {
			ModelAndView modelandview = new ModelAndView();
			List<MultipartFile> files = uploadItem.getFile();
			InputStream inputStream = null;		 
			for(MultipartFile file : files)
			{
				String fileName = file.getOriginalFilename();
				inputStream = file.getInputStream();
				byte[] arfffileData = IOUtils.toByteArray(inputStream);
				ArffFile arffFile = new ArffFile();
				arffFile.setFileName(fileName);
				arffFile.setFileContent(arfffileData);
				trainModelService.saveArffFile(arffFile);				
			}
			List<ArffFile> arffFiles = trainModelService.listAllArffs();
			modelandview.addObject("arffFiles", arffFiles);
			modelandview.addObject("message", "successUpload");
			modelandview.setViewName("trainSaveModel");
			return modelandview;
		
	 }
	 
	 @RequestMapping("downloadArff.htm")
	 public void downloadExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) throws IOException {
		ArffFile arffFile = trainModelService.getArffFileById(Integer.valueOf(id));

		ByteArrayInputStream in = new ByteArrayInputStream(arffFile.getFileContent());
		OutputStream outStream = response.getOutputStream();
		String fileName = URLEncoder.encode(arffFile.getFileName(), "UTF-8");
		fileName = URLDecoder.decode(fileName, "ISO8859_1");
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		while ((bytesRead = in.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
	 }
	 
	 @RequestMapping("deleteArff.htm")
	 public ModelAndView deleteArff(HttpServletRequest request)
	 {
		 ModelAndView modelandview = new ModelAndView();
		 Integer id = Integer.valueOf(request.getParameter("id"));
		 ArffFile arffFile = trainModelService.getArffFileById(id);
		 trainModelService.deleteArff(arffFile);		 
		 modelandview.setViewName("trainSaveModel");
		 List<ArffFile> arffFiles = trainModelService.listAllArffs();
         modelandview.addObject("arffFiles", arffFiles);
		 return modelandview;
	 }
	 
}



	
	
