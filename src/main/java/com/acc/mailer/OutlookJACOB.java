package com.acc.mailer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

public class OutlookJACOB
{
    private ActiveXComponent ol;
    private Dispatch outlook;
    private Object mapi[] = new Object[1];
    //private Object email[] = new Object[1];

    public OutlookJACOB()
    {
        mapi[0] = "MAPI";
    }

    public void createEmail(Map<String, Object> params)
    {
    	System.setProperty("jacob.dll.path", "C:/Users/renga.r.santh.ledge/Documents/Mailing Code/jacob-1.18-M2/jacob-1.18-M2/jacob-1.18-M2-x64.dll");
    	ol = new ActiveXComponent("Outlook.Application");
        outlook = ol.getObject();
        Dispatch.call(outlook,"GetNamespace",mapi).toDispatch();
        Dispatch mail = Dispatch.invoke(outlook, "CreateItem", Dispatch.Get, new Object[] { "0" }, new int[0]).toDispatch();
        
        Dispatch.put(mail, "Subject", params.get("subject"));
        Dispatch.put(mail, "Body", params.get("body"));
        
        /*String to[] = (String[]) params.get("to");*/
        String attachments[] = (String[]) params.get("attachments");
        /*String cc[] = (String[])params.get("cc");*/
        
        List<String> recipients = (ArrayList<String>)params.get("to");
        List<String> cCopy =  (ArrayList<String>)params.get("cc");
        
        if(!recipients.isEmpty())
        {
        	String recipient = "";
        	for(String re : recipients)
        	{
        		if(null != re)
        		{
        			recipient = re + "@accenture.com;";
        		}
        		 Dispatch.put(mail, "To", recipient);
        	}
        }

    /*    if(to != null)
        {
            if(to.length>0)
            {
                String _to = "";

                for(Object t : to)
                {
                	if(null != t)
                		_to += t + ";";
                		
                }

                Dispatch.put(mail, "To", _to);
            }
        }*/
        if(!cCopy.isEmpty())
        {
        	String carbonCopy = "";
        	for(String cc : cCopy)
        	{
        		if(null != cc)
        		{
        			carbonCopy = cc + "@accenture.com;";
        		}
        		 Dispatch.put(mail, "Cc", carbonCopy);
        	}
        }
      /*  if(cc != null)
        {
            if(cc.length>0)
            {
                String _cc = "";

                for(Object c : cc)
                {
                	if(null != c)
                		_cc += c + ";";
                		
                }

                Dispatch.put(mail, "Cc", _cc);
            }
        }*/
        

        if(attachments != null)
        {
            if(attachments.length>0)
            {
                Dispatch attachs = Dispatch.get(mail, "Attachments").toDispatch();

                for(Object attachment : attachments)
                {
                    Dispatch.call(attachs, "Add", attachment);
                }
            }
        }
        Dispatch.call(mail, "Send");
    }
}
