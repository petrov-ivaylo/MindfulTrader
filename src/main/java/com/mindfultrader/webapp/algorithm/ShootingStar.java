package com.mindfultrader.webapp.algorithm;


/* webappp.algorithm.Hammers.java
 * 
 * Checks last day's values to find a shooting star. 
 * If the open is higher and is in the lower third of the candle, it is recognized as a shooting star.
 * 
 * Author: team Golf 2020-2021 Aberdeen
 */

public class ShootingStar {
	
	static int weight = 7;
	
	/* Method to perform shooting star analysis.
	 * data is array of four float arrays.
	 */
	public static void readShootingStar(double[][] data, Results solution) {
		double max   = data[1] [data[0].length - 1];
		double min   = data[2] [data[0].length - 1];
		double open  = data[0] [data[0].length - 1];
		double close = data[3] [data[0].length - 1];
		// If difference between open and close is in the lower third of the candle, and if the candle is big enough to be reliable, then a shooting star is found. 
		if ( (close < open) && (close < (2*min + max)/3 ) && ((max - min)/close > 0.01) ){
			solution.modifyCounter(weight, false);
			solution.addResultToList("There is a shooting star, this is a simple indicator that the price will likely go down");
			System.out.println("Shooting star modified count by -7");
			}
		else {
			solution.addResultToList("There is no shooting star");
			System.out.println("Shooting star modified count by 0");
		}
			
	}

}
