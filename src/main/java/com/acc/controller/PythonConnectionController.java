package com.acc.controller;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PythonConnectionController {

	static Logger log = Logger.getLogger(PythonConnectionController.class.getName());
	@RequestMapping("python.htm")
	public ModelAndView trainSaveModel(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("connectToPython");
		return modelandview;
	}

	@RequestMapping("connectToPython.htm")
	public ModelAndView connectToPython(HttpServletRequest request, HttpServletResponse response) throws Exception {
		URL url = new URL("http://localhost:5000/hello/user");
		URLConnection urlcon = url.openConnection();
		InputStream stream = urlcon.getInputStream();
		int i;
		while ((i = stream.read()) != -1) {
			log.info((char)i);
		}
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("connectToPython");
		return modelandview;

	}

}
