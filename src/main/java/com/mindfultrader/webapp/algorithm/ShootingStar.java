package com.mindfultrader.webapp.algorithm;

/* 
 * Class with function to analyze data for shooting stars
 * The first function reads the last day of data, and if it presents an important pattern, the Shooting star, it gives advice accordingly.
 * If the close is lower than the open and is located in the bottom third of the candle, it is a shooting star. Otherwise no answer is given.
 * (WORK DONE)
 * 
 */


public class ShootingStar {
	
	static int weight = 7;
	
	/* Method to perform shooting star analysis.
	 * data is array of four float arrays. solution is a object from class.
	 */
	public static void readShootingStar(double[][] data, Results solution) {
		double max   = data[1] [data[0].length - 1];
		double min   = data[2] [data[0].length - 1];
		double open  = data[0] [data[0].length - 1];
		double close = data[3] [data[0].length - 1];
		
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
