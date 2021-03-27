package com.mindfultrader.webapp.controllers;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mindfultrader.webapp.algorithm.Algorithm;






@Controller
public class AlgorithmController {
	
	@Autowired
	JdbcTemplate jt;
	
	@RequestMapping("/algorithm/run")
	public ModelAndView run(String cmp_name)
	{
		String sql = "SELECT companies.Company_Symbol\r\n"
	    		+ "FROM companies\r\n"
	    		+ "WHERE (companies.Company_Name = ?)";

	    String symbol = (String) jt.queryForObject(
	            sql, String.class, cmp_name);
		
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
        
        
        JSONArray jSonPrices = (JSONArray) response.getBody().getObject().get("prices");
        
        
        
         double[] opens =  new double[90]; 
         double[] highs =  new double[90];
         double[] lows = new double[90];
         double[] closes = new double[90];
         
        
        
        Double x =1.0;
        for(int i=90; i>0 ; i-- ) {
            x = jSonPrices.getJSONObject(i).getDouble("open");
            opens[90-i] = x;
            x = jSonPrices.getJSONObject(i).getDouble("high");
            highs[90-i] = x;
            x = jSonPrices.getJSONObject(i).getDouble("low");
            lows[90-i] = x;
            x = jSonPrices.getJSONObject(i).getDouble("close");
            closes[90-i] = x;
        }
     
        double[][] data = {opens, highs, lows, closes};
		
		
		
		System.out.println("Creating algorithm objects...");
		Algorithm algo1 = new Algorithm(data);
		
		int[] torun = {1,2,3,4};
		
		
		algo1.runAlgo(torun);
		System.out.println("Algo1 run.");
		System.out.println(algo1.solution.getListOfResults());
		System.out.println(algo1.solution.getFinalAdvice());
		System.out.println(data[0][89]);
		
		
		
		
		
		
		//Create MVC object for webapp
		ModelAndView mv = new ModelAndView();
		mv.setViewName("algoresult");
		mv.addObject("conclusion1", algo1.solution.getFinalAdvice());
		mv.addObject("advice1", algo1.solution.getListOfResults());
		
		
		return mv;
	}
	
	@RequestMapping("/algorithm")
	public String home()
	{
		System.out.println("\n\n\nHI!!!! I'M HERE!!!! LALALLALALALALLAL\n\n\n");
		return "algorithm";
	}
}
