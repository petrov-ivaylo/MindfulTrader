package com.mindfultrader.webapp.algorithm;

/* webapp.algorithm.MovingAverage.java
 * 
 *  Calculates the moving average at each day over a 5,10 and 15 time span. Returns advice if the tendency of the last three days indicates a likely evolution.
 *  
 *  
 *  Author : Team Golf 2020-2021 Aberdeen
 *  */




public class MovingAverage {
	
	
	
	private static void reactingToMovingAverage(double[] movingAverage,double[][] data, Results solution, int scale) {
		//first if the open value is always above the moving Average it is a good sign of strength (the price is almost always increasing).
		int i = scale ;
		// the while loop goes through the whole list of days even if it found to go under at some point. This is bad efficiency but not a problem right now with a small number of functions.
		while(movingAverage[i]<data[0][i]){
			i++;
			if (i==movingAverage.length-1) {
				solution.addResultToList("The stock is always increasing over a scale of " + scale + "days");
				System.out.println("always above ma +5");
				solution.modifyCounter(5, true);
				return;
			}
		}
		int j = scale;
		while(movingAverage[j]>data[0][j]){
			j++;
			if (j==movingAverage.length-1) {
				solution.addResultToList("The stock is always decreasing over a scale of " + scale + "days");
				solution.modifyCounter(-5, true);
				System.out.println("always below ma -5");
				return;
			}
		}
		// calculates the tendency over the last three days. 
		double shortDerivative = (data[0][data[0].length-1] - data[0][data[0].length-4]);
		
		
		//case where the three last days go down in average
		if(shortDerivative <0) {
			// if we are above the moving average and going down, then it is likely going to drop until the ma
			if(data[0][data[0].length -1] > movingAverage[data[0].length-1]) {
				solution.addResultToList("Going down to the moving average of " + scale);
				solution.modifyCounter(-1, true);
				System.out.println("going down to ma -1");
			}
			//If the last day's value if below ma, but was higher before, then the ma line is broken, and is again a sign that the resitance disappeared.
			if((data[0][data[0].length -3]>movingAverage[data[0].length -3] || data[0][data[0].length-2] > movingAverage[data[0].length -2]) && data[0][data[0].length -1] < movingAverage[data[0].length-1] ) {
				solution.addResultToList("Broke by above the moving average of scale " + scale);
				solution.modifyCounter(-3, true);
				System.out.println("broken ma by above -3");
			}
			// if the price is going down again below the ma, then it is unpredictable and therefore a bad time to invest.
			if(data[0][data[0].length -1] < movingAverage[data[0].length-1] && data[0][data[0].length -2] < movingAverage[data[0].length-2] && data[0][data[0].length -3] < movingAverage[data[0].length-3])  {
				solution.addResultToList("Going down below the moving average of " + scale);
				solution.modifyCounter(-2, true);
				System.out.println("going down away from ma -1");
			}
			
		}
		
		//case where the price is going up
		if(shortDerivative >0) {
			
			
			// if we are under the moving average and going up, then it is likely going to go until the ma

			if(data[0][data[0].length -1] < movingAverage[data[0].length-1]) {
				solution.addResultToList("Going up to the moving average of " + scale);
				solution.modifyCounter(2, true);
				System.out.println("going up to ma +2");
			}
			//If the last day's value if above ma, but was lower before, then the ma line is broken, and is again a sign of strength.
			if((data[0][data[0].length -3]<movingAverage[data[0].length -3] || data[0][data[0].length-2] < movingAverage[data[0].length -2]) && data[0][data[0].length -1] > movingAverage[data[0].length-1] ) {
				solution.addResultToList("Broke by below the moving average of scale " + scale);
				solution.modifyCounter(3, true);
				System.out.println("broken ma by below +3");
			}
			// if the price is going up again above the ma, then it is a sign of strength, but less reliable.
			if(data[0][data[0].length -1] > movingAverage[data[0].length-1] && data[0][data[0].length -2] > movingAverage[data[0].length-2] && data[0][data[0].length -3] > movingAverage[data[0].length-3])  {
				solution.addResultToList("Going up above the moving average of " + scale);
				solution.modifyCounter(1, true);
				System.out.println("going up above ma +1");
			}
			
		}
	}
	
	
	
	public static void readMovingAverage(double[][] data, Results solution) {
		
		double average = 0.0;
		double[] movingAverage5 = new double[data[0].length];
		double[] movingAverage10 = new double[data[0].length];
		double[] movingAverage15 = new double[data[0].length];
		
		for(int i=5; i<data[0].length ; i++) {
			average=0;
			for(int j=5; j>0 ; j--) {
				average=(data[1][i-j]+data[2][i-j])/2;
				movingAverage5[i] += average;
			}
			movingAverage5[i] =movingAverage5[i]/5;
			
		}
		for(int i=10; i<data[0].length ; i++) {
			average=0;
			for(int j=10; j>0 ; j--) {
				average=(data[1][i-j]+data[2][i-j])/2;
				movingAverage10[i] += average;
			}
			movingAverage10[i] =movingAverage10[i]/10;
			
		}
		for(int i=15; i<data[0].length ; i++) {
			average=0;
			for(int j=15; j>0 ; j--) {
				average=(data[1][i-j]+data[2][i-j])/2;
				movingAverage15[i] += average;
			}
			movingAverage15[i] =movingAverage15[i]/15;
			
		}
		//System.out.println("Last day's open is " + data[0][data[0].length-1]);
		//System.out.println("moving average 5 is " + movingAverage5[data[0].length-2]);
		//System.out.println("moving average 10 is " + movingAverage10[data[0].length-2]);
		//System.out.println("moving average 15 is " + movingAverage15[data[0].length-2]);
		//System.out.println("opens = " + data[0][data[0].length-4] + " " +  data[0][data[0].length-3] + " " + data[0][data[0].length-2] + " " + data[0][data[0].length-1]);
		
		
		
		// double[] movingAverage5 = runMovingAverage(data,5);
		// double[] movingAverage10 = runMovingAverage(data,10);
		// double[] movingAverage15 = runMovingAverage(data,15);
		
		reactingToMovingAverage(movingAverage5, data, solution, 5);
		reactingToMovingAverage(movingAverage10, data, solution, 10);
		reactingToMovingAverage(movingAverage15, data , solution, 15);
	}
	
	
	
}