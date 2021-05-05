package com.mindfultrader.webapp.algorithm;

/* Third function, it reads the actual trend of the data, is it likely going up or down, and on which scale?
 * The function takes the derivative at each day and sums everything to determine the actual tendency. Recent days have a higher weight in the computation.
 * (WORK UNFINISHED 50% I'd say but I don't really know, should not be too hard)
 */

public class Trend {
	
	public static void readTrend(double[][] data, Results solution) {
		//we create variables that will contain the value of the derivative of the day, and the sum of those.
		double derivative = 0.0;
		double totalDerivatives = 0.0;
		//we only look at the opens, but we could look at the close as well.
		double[] opens = data[0];
		//we create a 'weight indicator' that will determine the importance of the derivative of the day we are looking at.
		double reversedIndex = 0.0;
		double relativeDerivatives =  0.0;
		
		//we look at all the days in the data and add their derivative according to their weight.
		for (int i = 0; i < (opens.length - 1); i++) {
			reversedIndex = opens.length - i;
			derivative = opens[i+1] - opens[i];
			totalDerivatives = totalDerivatives + (derivative * (1/(reversedIndex) ));
			relativeDerivatives = totalDerivatives/opens[opens.length -1];
			relativeDerivatives = relativeDerivatives*100;
		}
		
		//Modify result of full stock analysis according to findings
		System.out.println("trend modified the count by : " + relativeDerivatives);
		solution.modifyCounter(relativeDerivatives, true);
		
	}


}
