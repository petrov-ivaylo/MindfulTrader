/* 		webapp.algorithm.Algorithm 
 * 
 * Main file of the analysis algorithm used to predict the stock market.
 * Consists of constructor + analysis function caller and runAlgo function
 * 
 *  Author: team Golf 2020-2021 Aberdeen*/




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
	//solution contains the counter and the string of advice.
	public Results solution;
	//data contains four columns, each one being in this order the value of the 90 last day's 'open', 'high', 'low and 'close'.
	double[][] data;
	
	public Algorithm(double[][] data) {
		this.solution = new Results();
		this.data = data;
	}
	
	// calls the different analysis functions on the data, according to the list of 'functionsToRun'. A 1 in the list given runs shootingStar, a 2 runs hammer etc... inputting twice a number runs the function twice.
	// inputing different lists is used for testing purposes mainly. 
	private void analysisFunctions(int[] functionsToRun) {
		for (int i = 0; i < functionsToRun.length; i++) {
			if (functionsToRun[i] == 1) {
				Trend.readTrend(data, solution);
				
			}
			if (functionsToRun[i] == 2) {
				ShootingStar.readShootingStar(this.data, this.solution);
				
			}
			if (functionsToRun[i] == 3) {
				Hammers.readHammer(this.data, this.solution);
				
			}
			if (functionsToRun[i] == 4) {
				BouncingCurve.readBouncing(this.data, this.solution);
				
			}
			if (functionsToRun[i] == 5) {
				MovingAverage.readMovingAverage(this.data, this.solution);
				
			}
			
		}
	}
	
	
	// Main function of the analysis algorithm. Runs a data analysis and returns decision (buy/sell/nothing) based on counter.
	public void runAlgo(int[] functionsToRun) {
		analysisFunctions(functionsToRun);
		
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
	
