package com.acc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExecuteController {
	
	 @RequestMapping("executeData.htm")
     public ModelAndView executeData(HttpServletRequest request, HttpServletResponse response )
     {
                     ModelAndView modelandview = new ModelAndView();
                     modelandview.setViewName("executeData");
                     return modelandview;
     }
	 
	 @RequestMapping("executeBatchData.htm")
     public ModelAndView executeBatchData(HttpServletRequest request, HttpServletResponse response )
     {
                     ModelAndView modelandview = new ModelAndView();
                     modelandview.setViewName("executeBatchData");
                     return modelandview;
     }

}
