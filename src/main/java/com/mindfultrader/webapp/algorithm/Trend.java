package com.mindfultrader.webapp.algorithm;

public class Trend extends AnalysisFunction {
	
	/* 
	 * Function to analyse trend
	 */
	public static void readTrend(double[][] data, Results solution) {
		double derivative = 0.0;
		double totalDerivatives = 0.0;
		double[] opens = data[0];
		double rest = 0.0;
		
		for (int i = 0; i < (opens.length - 1); i++) {
			rest = opens.length - i;
			derivative = opens[i+1] - opens[i];
			totalDerivatives = totalDerivatives + (derivative * (1/(rest) ));
		}
		
		solution.modifyCounter(totalDerivatives, true);
		
	}


}
