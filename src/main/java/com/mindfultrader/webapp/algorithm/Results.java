package com.mindfultrader.webapp.algorithm;

public class Results {

	int counter;
	//ArrayList<String> ListOfResults;
	String ListOfResults;
	String finalAdvice;
	
	public Results () {
		this.counter = 0;
		//this.ListOfResults = new ArrayList<>();
		this.ListOfResults = new String();

	}
	
	// method to modify decision counter. Param: i is amount to change, b determines subtract (false) or add (true) 
	public void modifyCounter(double d, boolean b) {
		if (b==false) counter -= d; 
		else if (b==true) counter += d;
	}
	
	public void addResultToList(String adviceToAdd) {
		//ListOfResults.add(adviceToAdd);
		ListOfResults += adviceToAdd;
		ListOfResults += ". ";
	}
	
	
	// Scope of getters is package
	int getCounter() {
		return counter;
	}

	/*public ArrayList<String> getListOfResults() {
		return ListOfResults;
	}*/
	
	public String getListOfResults() {
		return ListOfResults;
	}

	public String getFinalAdvice() {
		return finalAdvice;
	}

}
