package com.acc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.acc.dto.ArffFile;
import com.acc.service.TrainModelService;

@Controller
public class TrainSaveModelController {
	@Autowired
	TrainModelService trainModelService;
	
	 @RequestMapping("trainSaveModel.htm")
     public ModelAndView trainSaveModel(HttpServletRequest request, HttpServletResponse response )
     {
		ModelAndView modelandview = new ModelAndView();
		List<ArffFile> arffFiles = trainModelService.listAllArffs();
		modelandview.addObject("arffFiles", arffFiles);
		modelandview.setViewName("trainSaveModel");
		return modelandview;
     }

}
