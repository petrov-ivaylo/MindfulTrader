package com.mindfultrader.webapp.algorithm;

/* webappp.algorithm.Hammers.java
 * 
 * The second function works the same way as the shooting star. It checks the last day to see if it is a hammer.
 * If the close if higher and is in the upper third of the candle, it is recognized as a Hammer.
 * 
 * Author: team Golf 2020-2021 Aberdeen
 */

public class Hammers {
	
	static int weight = 7;

	public static void readHammer(double[][] data, Results solution) 
	{
    	double max 	 = data[1][data[0].length - 1];
    	double min	 = data[2][data[0].length - 1];
    	double open  = data[0][data[0].length - 1];
    	double close = data[3][data[0].length - 1];
    	// If difference between open and close is in the higher third of the candle, and if the candle is big enough to be reliable, then a hammer is found. 
    	if ( (close > open) && (open > (min + 2*max)/3) && ((max - min)/close > 0.01)){
    		//Increase counter and add advice if hammer found
         	solution.modifyCounter(weight, true);
         	solution.addResultToList("There is a Hammer, this is a simple indicator that the price will most likely go up.");
        } 
    	else {
    		solution.addResultToList("There is no hammer pattern");
    	}
	}

}
