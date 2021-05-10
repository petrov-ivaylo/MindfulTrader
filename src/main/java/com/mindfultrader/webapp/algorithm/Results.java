package com.mindfultrader.webapp.algorithm;
/* webapp.algorithm.Results
 * 
 * Utility file giving the basic functions to modify the counter.add advice etc...
 *
 *Author : team Golf 2020-2021 Aberdeen
 */

public class Results {

	int counter;
	
	String ListOfResults;
	String finalAdvice;
	
	public Results () {
		this.counter = 0;
		
		this.ListOfResults = new String();

	}
	
	// method to modify decision counter. Param: i is amount to change, b determines subtract (false) or add (true) 
	public void modifyCounter(double d, boolean b) {
		if (b==false) counter -= d; 
		else if (b==true) counter += d;
	}
	
	public void addResultToList(String adviceToAdd) {
	
		ListOfResults += adviceToAdd;
		ListOfResults += ". ";
	}
	
	
	// Scope of getters is package
	int getCounter() {
		return counter;
	}

	
	public String getListOfResults() {
		return ListOfResults;
	}

	public String getFinalAdvice() {
		return finalAdvice;
	}

}
