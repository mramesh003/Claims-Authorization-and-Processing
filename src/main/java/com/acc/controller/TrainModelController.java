package com.acc.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.acc.dto.CsvFile;
import com.acc.entity.FileUpload;
import com.acc.service.PrepareTrainDataService;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import org.apache.log4j.Logger;

@Controller
public class TrainModelController {
	
	@Autowired
	PrepareTrainDataService prepareTrainDataService;

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
	 public ModelAndView convertToArff(HttpServletRequest request, HttpServletResponse response, FileUpload uploadItem) throws IOException {
		 ModelAndView modelandview = new ModelAndView();
		 List<MultipartFile> files = uploadItem.getFile();
		 log.info("Hello this is a debug message");
		 InputStream inputStream = null;
		 for(MultipartFile file : files){
				//if (file.getSize() > 0)
					inputStream = file.getInputStream();
					log.info("aaaaaaaaaa"+inputStream); 
				byte[] fileData = IOUtils.toByteArray(inputStream);
				String fileName = file.getOriginalFilename();
				int position = fileName.lastIndexOf(".");
				String fileType = fileName.substring(position);
				log.info(fileData+ " "+fileName+ " "+ fileType);
				
				/*File convFile = new File(file.getOriginalFilename());
			    convFile.createNewFile();
			    convFile.*/
				
				
				CSVLoader loader = new CSVLoader();
				 loader.setSource(inputStream);
				 Instances data = loader.getDataSet();
				 
				 ArffSaver saver = new ArffSaver();
				saver.setInstances(data);
				
			    saver.setFile(new File("test.arff"));
			    saver.writeBatch();
			}
		 modelandview.setViewName("prepareTrainingModel");
         return modelandview;
	 }
}



	
	
