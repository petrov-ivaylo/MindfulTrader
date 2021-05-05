package com.mindfultrader.webapp.algorithm;

/*
 * The second function works the same way as the shooting star. If now checks the last day to see if it is a hammer.
 * If the close if higher and is in the upper third of the candle, it is recognized as a Hammer.
 * (WORK DONE)
 * 
 */

public class Hammers {
	
	static int weight = 7;

	public static void readHammer(double[][] data, Results solution) 
	{
    	double max 	 = data[1][data[0].length - 1];
    	double min	 = data[2][data[0].length - 1];
    	double open  = data[0][data[0].length - 1];
    	double close = data[3][data[0].length - 1];

    	if ( (close > open) && (open > (min + 2*max)/3) && ((max - min)/close > 0.01)){
    		//Increase counter and add advice if hammer found
         	solution.modifyCounter(weight, true);
         	solution.addResultToList("There is a Hammer, this is a simple indicator that the price will most likely go up.");
         	System.out.println("Hammer modified count by +7");
        } 
    	else {
    		solution.addResultToList("There is no hammer pattern");
    		System.out.println("Hammer modified count by +0");
    	}
	}

}
