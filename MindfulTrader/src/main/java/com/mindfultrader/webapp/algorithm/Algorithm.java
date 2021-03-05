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
				Trend.readTrend(data, solution);
			}
			if (functionsToRun[i] == 2) {
				ShootingStar.readShootingStar(this.data, this.solution);
			}
			if (functionsToRun[i] > 2) {
				System.out.println("Sorry, we don't have so many functions implemented yet.");
			}
		}
	}
	
	public void runAlgo(int[] functionsToRun) {
		analysisFunctions(functionsToRun);
		if (this.solution.counter < -5) {
			solution.finalAdvice = "You should sell your shares if you own some in this company.";
		}
	    if ((solution.counter>-4) && (solution.counter<8)){
	    	solution.finalAdvice = "There is not enough information to choose between selling and buying.";
    	}
    	if (solution.counter>7){
	    	solution.finalAdvice = "This stock seems to be increasing its value, we recommend you to buy a share in this company.";
	    }
	}
}
	
