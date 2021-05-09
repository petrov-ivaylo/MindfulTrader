package com.mindfultrader.webapp.algorithm;
/* webapp.algorithm.RequestData.java
 * 
 * Method to make a request to the API and get the historical data from given company
 * 
 * Author: team Golf 2020-2021 Aberdeen
 *  */


import org.json.JSONArray;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class RequestData {
	
	
	
	public static double[][] dataRequest (String symbol){
		
		//unirest request
        String uri = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v3/get-historical-data?symbol=" + symbol + "&region=US";
        HttpResponse<JsonNode> response = null;
        System.out.println("Requesting data from " + symbol);
        try {
            response = Unirest.get(uri)
                    .header("x-rapidapi-key", "ae107f19eamsh243b1c0d79c570bp1e7f3cjsn221a321d5935")
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
         
       
        
        double x =1.0;
        
        //j represents the number of days to exclude, it has only a testing purpose (and also making sure we do not add weird values to the list)
        //In the final state of the algo, it will be set correctly to 0, if it is not right now, then it means someone is testing the software accuracy
        
        int j = 0;
        for(int i=0; i<90 ; i++ ) {
        	
        
        	while (!jSonPrices.getJSONObject(j).has("open")){
        		j++;
        		}
        	
        	x = jSonPrices.getJSONObject(j).getDouble("open");
            opens[opens.length-1 - i] = x;
            
        	
            x = jSonPrices.getJSONObject(j).getDouble("high");
            highs[opens.length-1 -i] = x;
        	
        	
            x = jSonPrices.getJSONObject(j).getDouble("low");
            lows[opens.length-1 -i] = x;
        	
        	
            x = jSonPrices.getJSONObject(j).getDouble("close");
            closes[opens.length-1 -i] = x;
        	
            j++;
            
            
        }
        System.out.println("Last open is " + opens[opens.length-1] );
        double[][] data = {opens, highs, lows, closes};
        return data;
	}
	
}
