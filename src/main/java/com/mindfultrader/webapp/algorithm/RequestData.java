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
        
        double x =1.0;
        
        //j represents the number of days to exclude, it has only a testing purpose (and also making sure we do not add weird values to the list)
        //In the final state of the algo, it will be set correctly to 0, if it is not right now, then it means someone is testing the software accuracy
        
        int j = 15;
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
     
        double[][] data = {opens, highs, lows, closes};
        return data;
	}
	
}
