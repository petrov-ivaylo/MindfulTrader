package com.mindfultrader.webapp.algorithm;

/* 
 * Class with function to analyze data for shooting stars
 */


public class ShootingStar extends AnalysisFunction {
	
	static int weight = 10;
	
	/* Method to perform shooting star analysis.
	 * data is array of four float arrays. solution is a object from class.
	 */
	public static void readShootingStar(double[][] data, Results solution) {
		double max   = data[1] [data[0].length - 1];
		double min   = data[2] [data[0].length - 1];
		double open  = data[0] [data[0].length - 1];
		double close = data[3] [data[0].length - 1];
		
		if ( (close < open) && (close < (2*min + max)/3 ) ){
			solution.modifyCounter(weight, false);
			solution.addResultToList("There is a shooting star, this is a simple indicator that the price will likely go down");
					}
		else {
			solution.addResultToList("There is no shooting star");
		}
			
	}

}
