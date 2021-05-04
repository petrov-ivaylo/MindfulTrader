package com.mindfultrader.webapp.algorithm;

import org.json.JSONArray;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class RequestData {
	
	
	
	
	public static double[][] dataRequest (String symbol){
		
		
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
         
        //System.out.println(jSonPrices.getJSONObject(0));
        
        Double x =1.0;
        for(int i=90; i>0 ; i-- ) {
        	System.out.println(91-i);
        	if (jSonPrices.getJSONObject(i).has("open")){
        		x = jSonPrices.getJSONObject(i).getDouble("open");
        		opens[90-i] = x;
        		}
        	else {
        		i--;
                x = jSonPrices.getJSONObject(i).getDouble("open");
                opens[90-i] = x;
                }
        	if (jSonPrices.getJSONObject(i).has("high")){
            x = jSonPrices.getJSONObject(i).getDouble("high");
            highs[90-i] = x;
        	}
        	if (jSonPrices.getJSONObject(i).has("low")){
            x = jSonPrices.getJSONObject(i).getDouble("low");
            lows[90-i] = x;
        	}
        	if (jSonPrices.getJSONObject(i).has("close")){
            x = jSonPrices.getJSONObject(i).getDouble("close");
            closes[90-i] = x;
        	}
        }
     
        double[][] data = {opens, highs, lows, closes};
        return data;
	}
	
}