package com.acc.mailer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acc.mailer.OutlookJACOB;

public class Mailer {
	
	public static void triggerMail(String body, String subject, List<String> recipients, List<String> cCopy)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("subject",subject);
		param.put("body", body); 
		List<String> toList = new ArrayList<String>();
		toList.add("renga.r.santh.ledge@accenture.com");    // Used for testing
		List<String> ccList = new ArrayList<String>();
		ccList.add("p.p.ramakrishnan@accenture.com");	    // Used for testing
		param.put("to", toList);
		param.put("cc",ccList);

		OutlookJACOB mail = new OutlookJACOB();
		mail.createEmail(param);
	}

}
