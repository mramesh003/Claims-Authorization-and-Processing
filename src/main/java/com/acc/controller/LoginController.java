package com.acc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.acc.dto.ExcelFile;
import com.acc.dto.User;
import com.acc.service.LoginService;
import com.acc.service.PrepareTrainDataService;

@Controller
public class LoginController {
	@Autowired
	LoginService LoginService;
	
	@Autowired
	PrepareTrainDataService prepareTrainDataService;

	static Logger log = Logger.getLogger(LoginController.class.getName());
	@RequestMapping("Login-redirect.htm")
	public ModelAndView loginRedirect(HttpServletRequest request, HttpServletResponse response )
	{  
		return new ModelAndView("login");
	}
	
	@RequestMapping(value= {"login.htm"}, method = RequestMethod.POST)
	
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response )
	{
		HttpSession session = request.getSession();
		ModelAndView modelandview = new ModelAndView();
		String username = request.getParameter("Username");
		String password = request.getParameter("pw");
		User user=LoginService.checkUser(username);
		
		if(user.getUserName() !=null) 
		{
			if(password.equals(user.getPassword()))
			{
				session.setAttribute("user", user);
				if (user.getUserName() == "admin") {
					List<ExcelFile> excelFiles = prepareTrainDataService.listAllExcels();
					modelandview.addObject("excelFiles", excelFiles);
					modelandview.setViewName("prepareTrainingData");
				} else if (user.getUserName() == "user") {
					modelandview.setViewName("userTestModel");
				}
			}
		}
		else
		{
			modelandview.setViewName("login");

		}
		return modelandview;
	}
	@RequestMapping("logout.htm")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response )
	{
		HttpSession session = request.getSession();
		session.invalidate();
		return new ModelAndView("login");
	}

}
