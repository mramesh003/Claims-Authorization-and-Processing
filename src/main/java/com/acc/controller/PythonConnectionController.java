package com.acc.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
			log.info((char) i);
		}
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("connectToPython");
		return modelandview;
	}

	@RequestMapping("executeeModel.htm")
	public ModelAndView executeModel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") String id) throws Exception {
		String baseUrl = "http://localhost:5000/hello/";
		String completeUrl = baseUrl + id;
		URL url = new URL(completeUrl);
		URLConnection urlcon = url.openConnection();
		InputStream stream = urlcon.getInputStream();
		String line = null;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		List<List<String>> ListOfClaims = new ArrayList<List<String>>();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String result = sb.toString();
		String claims[] = result.split(";");
		for (int i = 0; i < claims.length; i++) {
			List<String> claimData = new ArrayList<String>();
			String x = claims[i];
			String a = x.substring(1, x.length() - 1);
			String claim[] = a.split(",");
			for (int j = 0; j < claim.length; j++)
				claimData.add(claim[j]);
			ListOfClaims.add(claimData);
		}
		
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject("ListOfClaims", ListOfClaims);
		modelandview.setViewName("connectToPython");
		return modelandview;
	}
}
