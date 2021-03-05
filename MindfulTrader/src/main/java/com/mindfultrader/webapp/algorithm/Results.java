package com.mindfultrader.webapp.algorithm;

/* Class consisting of ListOfResults and CountYesNo in detailed design combined.
 * Will create an object which holds the counter to determine advice and 
 * the list of advice from the individual analysis functions.
 * 
 * A few references for java:
 * https://www.geeksforgeeks.org/arraylist-in-java/
 */

import java.util.ArrayList;

public class Results {

	int counter;
	ArrayList<String> ListOfResults;
	String finalAdvice;
	
	public Results () {
		this.counter = 0;
		this.ListOfResults = new ArrayList<>();
	}
	
	// method to modify decision counter. Param: i is amount to change, b determines subtract (false) or add (true) 
	public void modifyCounter(double d, boolean b) {
		if (b==false) counter -= d; 
		else if (b==true) counter += d;
	}
	
	public void addResultToList(String adviceToAdd) {
		ListOfResults.add(adviceToAdd);
	}
	
	
	// Scope of getters is package
	int getCounter() {
		return counter;
	}

	public ArrayList<String> getListOfResults() {
		return ListOfResults;
	}

	public String getFinalAdvice() {
		return finalAdvice;
	}

}
