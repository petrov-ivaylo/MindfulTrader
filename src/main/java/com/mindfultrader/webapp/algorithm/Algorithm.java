package com.mindfultrader.webapp.algorithm;

import org.springframework.stereotype.Component;

/*
 * Class to put together algorithm - controller pattern
 * 
 * A few java references:
 * https://www.w3schools.com/java/java_class_methods.asp
 * 
 * 
 * Note: Access modifiers need to be changed for info hiding and general good practice...
 * Note: we need to work with autowiring instead
 */

@Component
public class Algorithm
{
	
	public Results solution;
	
	double[][] data;
	
	public Algorithm(double[][] data) {
		this.solution = new Results();
		this.data = data;
	}
	
	// function to run analysis functions
	private void analysisFunctions(int[] functionsToRun) {
		for (int i = 0; i < functionsToRun.length; i++) {
			if (functionsToRun[i] == 1) {
				System.out.println("Running trend analysis...");
				Trend.readTrend(data, solution);
				
			}
			if (functionsToRun[i] == 2) {
				System.out.println("Running shooting star analysis...");
				ShootingStar.readShootingStar(this.data, this.solution);
				
			}
			if (functionsToRun[i] == 3) {
				System.out.println("Running hammer analysis...");
				Hammers.readHammer(this.data, this.solution);
				
			}
			if (functionsToRun[i] == 4) {
				System.out.println("Running bouncing curve analysis...");
				BouncingCurve.readBouncing(this.data, this.solution);
				
			}
			if (functionsToRun[i] == 5) {
				System.out.println("Running moving average analysis analysis...");
				MovingAverage.readMovingAverage(this.data, this.solution);
				
			}
			
		}
	}
	
	public void runAlgo(int[] functionsToRun) {
		analysisFunctions(functionsToRun);
		System.out.println("count is = " + this.solution.counter + "\n");
		
		if (this.solution.counter <= -5) {
			solution.finalAdvice = "You should sell your shares if you own some in this company.";
		}
	    if ((solution.counter>-5) && (solution.counter<7)){
	    	solution.finalAdvice = "There is not enough information to choose between selling and buying.";
    	}
    	if (solution.counter>=7){
	    	solution.finalAdvice = "This stock seems to be increasing its value, we recommend you to buy a share in this company.";
	    }
	}
}
	
