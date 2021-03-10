package com.mindfultrader.webapp.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mindfultrader.webapp.data.TestDatabase;

@Controller
public class PortfolioCotroller {
	
	@GetMapping(value = "/portfolio", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String listUsers(Model model) {
		
		//System.out.println(TestDatabase.getData());
		
			   String sb = new String();
			   sb += "<select>";
			   for(String name:TestDatabase.getData()) {
				   //System.out.println("<option value=\""+name+"\">"+name+"</option>");
			      sb += "<option value=\""+name+"\">"+name+"</option>";}
			   sb += "</select>";
			   
			   return sb;
	}

}

