package com.mindfultrader.webapp.algorithm;

public class BouncingCurve {
	
	static class Limits{
		double ceiling = 0;
		double floor   = 0;
	}

	//This is a subfunction from the next Analysis Function. I'd advice you to look at the next one first to understand everything.
	//We look at the small scale summits and floors. We use this information to determine the 'safety net' and ceiling of the stock values. According advice not yet implemented, here I only they if there is such ceiling or floor.
	
	//(WORK UNFINISHED 70% I'd say but my opinion is often worth peanuts when I look at it in retrospective)
	public static Limits bouncing1 (double[][] data){
		int[] indexsummits = new int[90];
		int[] indexlow = new int[90];
		int nbsummits = 0;
		int nblow = 0;
		Limits limit = new Limits();
		 
		//I created variables, a list of the days where I found a summit/floor and their number. I only keep 10 values here (and use only three) 'cause I will add different scales again.
		 
		// I create both booleans. They are set to false by default, and if the summits/low do not indicate the existence of a ceiling/floor then they are never changed.
		boolean ceiling = false;
		boolean floor = false;
		 
		 
		//That's Hell you're walking into.
		for (int i =2; i<data[0].length - 2; i++){
	    	// I check everyday if its neighbours (two days before, two days after) are superior or inferior. If it is superior/inferior to all of them then it is listed as a summit/low.
	    	if ( (data[1][i]>data[1][i-1])&&(data[1][i]>data[1][i-2])&&(data[1][i]>data[1][i+1])&&(data[1][i]>data[1][i+2]) ){
	        	if(nbsummits<10){
	            	//I add its index to the table and increase the count by one
	            	indexsummits[nbsummits] = i;
	            	nbsummits += 1;}
	    	}
	    	if ( (data[1][i]<data[1][i-1])&&(data[1][i]<data[1][i-2])&&(data[1][i]<data[1][i+1])&&(data[1][i]<data[1][i+2]) ){
	        	if (nblow<10){
	            	indexlow[nblow] = i;
	            	nblow += 1;}
	    	}
	   	 
		}
		//I have to do special cases for the two last days of the data because they have no neighbour after them (I could technically do the same with the first values but it is mostly irrelevant on 300+ values)
		if ( (data[1][data[1].length-2]>data[1][data[1].length-3])&&(data[1][data[1].length-2]>data[1][data[1].length-1])&&(data[1][data[1].length-2]>data[1][data[1].length-4]) ){
	        	indexsummits[nbsummits] =data[1].length-2;
	        	nbsummits += 1;
	    	}
		if ( (data[1][data[1].length-2]<data[1][data[1].length-3])&&(data[1][data[1].length-2]<data[1][data[1].length-4])&& (data[1][data[1].length-2]<data[1][data[1].length-1])){
	        	indexlow[nblow] = data[1].length-2;
	        	nblow += 1;
	    	}
		if ( (data[1][data[1].length-1]>data[1][data[1].length-2])&&(data[1][data[1].length-1]>data[1][data[1].length-3]) ){
	        	indexsummits[nbsummits] =data[1].length-1;
	        	nbsummits += 1;
	    	}
		if ( (data[1][data[1].length-1]<data[1][data[1].length-2])&&(data[1][data[1].length-1]<data[1][data[1].length-3]) ){
	        	indexlow[nblow] = data[1].length-1;
	        	nblow += 1;
	    	}
	    	//done
		 
		//If I found enough summits/low I compare them, and if I find that they have less than 10% of difference in value I consider this a floor/ceiling
		if (nbsummits > 2){
	    	if ( ((data[1][indexsummits[nbsummits-1]]/data[1][indexsummits[nbsummits-2]])<1.2) && ((data[1][indexsummits[nbsummits-2]]/(data[1][indexsummits[nbsummits-3]])>0.8)) && (data[1][indexsummits[nbsummits-2]]/(data[1][indexsummits[nbsummits-3]])<1.2) ){
	        	limit.ceiling = data[1][indexsummits[nbsummits-1]];
	       	 
	    	}
		}    
		if (nblow > 2){
	    	if ( ((data[1][indexlow[nbsummits-1]]/data[1][indexlow[nblow-2]])<1.2) && ((data[1][indexlow[nblow-2]]/(data[1][indexlow[nblow-3]])>0.8)) && (data[1][indexlow[nblow-2]]/(data[1][indexlow[nblow-3]])<1.2) ){
	        	limit.floor = data[1][indexlow[nbsummits-1]];
	    	}	 
		}
		 
		// I return the values of a potential floor or ceiling.
		 
		return limit;
		 
	}
	
	public static int reactionPattern (Limits estimation){
		return 0;
		 
	}
	
	//Analysis Function that tries to find a pattern of ceiling or floor repeating itslef, to find a 'safety net' to know where the value is most likely going to go up or down.
	//(WORK UNFINISHED 60% I'd say)
	public static void readBouncing (double[][] data, Results solution){
		//I'm planning on doing multiple functions that look at the summits/low on diffrent scales. here is only the most basic one.
		Limits estimation1= bouncing1(data);
		 
		 
		if (estimation1.ceiling != 0 && estimation1.floor != 0){
	    	
			String advice = "Recurring pattern found. Ceiling found at " + estimation1.ceiling + " and floor at " + estimation1.floor;
			solution.addResultToList(advice);
	    	
	    	int actionToTake = reactionPattern(estimation1);
	    	
	    	if (actionToTake == -1)	{solution.modifyCounter(10, false) ;}
	    	if (actionToTake ==  0)	{solution.modifyCounter(3, true)   ;}
	    	if (actionToTake ==  1)	{solution.modifyCounter(3, false)  ;}
	    	if (actionToTake ==  2)	{solution.modifyCounter(4, true)   ;}
		}
		 
		else {
	    	if (estimation1.ceiling != 0 ){
		    	String advice = "There is a ceiling at " + estimation1.ceiling;
		    	solution.addResultToList(advice);
	    	}
		 
	    	if (estimation1.floor != 0){
	    		String advice = "There is a floor at " + estimation1.floor;
	    		solution.addResultToList(advice);
	    	}
		}
	}
}
