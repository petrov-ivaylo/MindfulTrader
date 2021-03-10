package com.mindfultrader.webapp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;






@Controller 
public class Data2Controller {
    @RequestMapping("/requestdata2/run")
    public ModelAndView run(String symbol) 
    {
        String uri = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v3/get-historical-data?symbol=" + symbol + "&region=US";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(uri)
                    .header("x-rapidapi-key", "71c138c430msh94f21a0e7d20608p1bab39jsnfc8e8fcb1aab")
                    .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                    .asJson();
        } catch (UnirestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("Algorithm has run :) ");
        System.out.println(response.getBody());
        
        //Create MVC object for webapp
                ModelAndView mv = new ModelAndView();
                mv.setViewName("dataresult");
                mv.addObject("conclusion1", response.getBody());
                
                
        return mv;
        

    }
}