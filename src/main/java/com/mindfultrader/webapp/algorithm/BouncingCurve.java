/* webapp.algorithm.BouncingCurve
 * 
 * One of the analysis functions called in Algorithm.java. Searches the data to find a 'ceiling' and a 'floor'. Both of which are very important values in stock market analysis.
 * Finds a recurring maximum/minimum value in the 'high'/low values and if those are present more than three times, considers it a ceiling/floor.
 * 
 * Author : team Golf 2020-2021 Aberdeen
 * */


package com.mindfultrader.webapp.algorithm;




public class BouncingCurve {
	
	static class Limits{
		double ceiling = 0;
		double floor   = 0;
	}

	//Subfunction from the main bouncingCurve function. I'd advice you to look at the next one first to understand everything.
	//We look at the small scale summits and floors. We use this information to determine the 'safety net' and ceiling of the stock values.
	
	
	public static Limits bouncing1 (double[][] data){
		// variables to keep track of the index of the peaks/lows I find.
		int[] indexsummits = new int[90];
		int[] indexlow = new int[90];
		int nbsummits = 0;
		int nblow = 0;
		Limits limit = new Limits();
		 
		
		// the next for loop looks at every high/low value and compares it to its neighbours. If they are superior/inferior to both, the index is added to the list. 
		for (int i =2; i<data[0].length - 2; i++){
	    	
	    	if ( (data[1][i]>data[1][i-1])&&(data[1][i]>data[1][i-2])&&(data[1][i]>data[1][i+1])&&(data[1][i]>data[1][i+2]) ){
	        	if(nbsummits<10){
	            	//I add its index to the table and increase the count by one
	            	indexsummits[nbsummits] = i;
	            	nbsummits += 1;}
	    	}
	    	if ( (data[2][i]<data[2][i-1])&&(data[2][i]<data[2][i-2])&&(data[2][i]<data[2][i+1])&&(data[2][i]<data[2][i+2]) ){
	        	if (nblow<10){
	            	indexlow[nblow] = i;
	            	nblow += 1;}
	    	}
	   	 
		}
		//I have to do special cases for the next-to-last day of the data because they have no neighbour after them (I could technically do the same with the first values but it is mostly irrelevant on 300+ values)
		if ( (data[1][data[1].length-2]>data[1][data[1].length-3])&&(data[1][data[1].length-2]>data[1][data[1].length-1])&&(data[1][data[1].length-2]>data[1][data[1].length-4]) ){
	        	indexsummits[nbsummits] =data[1].length-2;
	        	nbsummits += 1;
	    	}
		if ( (data[2][data[1].length-2]<data[2][data[1].length-3])&&(data[2][data[1].length-2]<data[2][data[1].length-4])&& (data[2][data[1].length-2]<data[2][data[1].length-1])){
	        	indexlow[nblow] = data[2].length-2;
	        	nblow += 1;
	    	}
    	
		 
		//If I found enough summits/low I compare them, and if I find that they have less than 10% of difference in value I consider this a floor/ceiling. Using a 10% difference makes this analysis more reliable on small companies as for values over 3000 USD a 300 dollar makes a huge difference.
		if (nbsummits > 2){
	    	if ( 
	    			   ( (data[1][indexsummits[nbsummits-1]]/data[1][indexsummits[nbsummits-2]])<1.1) 
	    			&& ( (data[1][indexsummits[nbsummits-1]]/data[1][indexsummits[nbsummits-2]])>0.9)
	    			
	    			&&  (data[1][indexsummits[nbsummits-2]]/(data[1][indexsummits[nbsummits-3]])>0.9)
	    			&&  (data[1][indexsummits[nbsummits-2]]/(data[1][indexsummits[nbsummits-3]])<1.1)
	    			
	    			&&  (data[1][indexsummits[nbsummits-2]]/(data[1][indexsummits[nbsummits-3]])<1.1)
	    			&&  (data[1][indexsummits[nbsummits-2]]/(data[1][indexsummits[nbsummits-3]])>0.9)
	    			){
	        	limit.ceiling = data[1][indexsummits[nbsummits-1]];
	       	 
	    	}
		}    
		if (nblow > 2){
			if ( 
	    			   ( (data[2][indexlow[nblow-1]]/data[2][indexlow[nblow-2]])<1.1) 
	    			&& ( (data[2][indexlow[nblow-1]]/data[2][indexlow[nblow-2]])>0.9)
	    			
	    			&&  (data[2][indexlow[nblow-2]]/(data[2][indexlow[nblow-3]])>0.9)
	    			&&  (data[2][indexlow[nblow-2]]/(data[2][indexlow[nblow-3]])<1.1)
	    			
	    			&&  (data[2][indexlow[nblow-2]]/(data[2][indexlow[nblow-3]])<1.1)
	    			&&  (data[2][indexlow[nblow-2]]/(data[2][indexlow[nblow-3]])>0.9)
	    			){
	        	limit.floor = data[1][indexlow[nblow-1]];
	       	 
	    	}
		}  
		 
		// I return the values of a potential floor or ceiling. It is 0 if none if found.
		 
		return limit;
		 
	}
	
	public static int reactionPattern (Limits estimation, double[][]data){
	        int length = data[1].length;

	        //Compares last days value to the floor and ceiling. If it breaks the ceiling, then it can stop anytime and no advice can be given via bouncing curves. 
	        // If the last day breaks the floor, this is a very good hint that the price will probably crahs down, as the resistance that kept the price up disappeared.
	        if(  (data[1][length-1] > estimation.ceiling)  ) {return 0;}
	        if( (data[2][length-1] < estimation.floor)  ) {return -10;}
	        else { 

	            //I take the current value of the stock. The closer it is to the ceiling the most likely it is to go down, the closer it is to the floor, the most likely it is to go up.
	            double currentvalue = data[3][length -1];
	            int actionToTake = 1;
	            double distance = estimation.ceiling - estimation.floor;
	            double relativeActualValue = currentvalue - estimation.floor;
	            if(relativeActualValue*2 > distance) {
	                //I will look at how far the value is from the middle of ceiling-floor. If it is higher then I will return a negative result. otherwise I add a positive int.
	                actionToTake = actionToTake*(-1);
	                relativeActualValue = relativeActualValue-distance/2;
	                }
	            else {relativeActualValue = distance/2 - relativeActualValue;}
	            distance = distance/2;
	            // I see in which % the value is (relative to the distance to the middle of the ceiling-floor) and modify the value more or less given how far it is from it.
	            double distanceToBetweenCeilingAndFloor = relativeActualValue/distance;
	            if(distanceToBetweenCeilingAndFloor < 0.1) {return actionToTake*0;}
	            if(distanceToBetweenCeilingAndFloor < 0.2) {return actionToTake*1;}
	            if(distanceToBetweenCeilingAndFloor < 0.3) {return actionToTake*2;}
	            if(distanceToBetweenCeilingAndFloor < 0.4) {return actionToTake*3;}
	            if(distanceToBetweenCeilingAndFloor < 0.5) {return actionToTake*4;}
	            if(distanceToBetweenCeilingAndFloor < 0.6) {return actionToTake*5;}
	            if(distanceToBetweenCeilingAndFloor < 0.7) {return actionToTake*6;}
	            if(distanceToBetweenCeilingAndFloor < 0.8) {return actionToTake*7;}
	            if(distanceToBetweenCeilingAndFloor < 0.9) {return actionToTake*8;}
	            return actionToTake*10;

	        }
	    }
		 
	
	
	//Analysis Function that tries to find a pattern of ceiling or floor repeating itslef, to find a 'safety net' to know where the value is most likely going to go up or down.

	public static void readBouncing (double[][] data, Results solution){
		Limits estimation1= bouncing1(data);
		 
		 // ceiling is != 0 if a ceiling was found. Same for the floor. 
		if (estimation1.ceiling != 0 && estimation1.floor != 0){
	    	
			String advice = "Recurring pattern found. Ceiling found at " + estimation1.ceiling + " and floor at " + estimation1.floor;
			solution.addResultToList(advice);
	    	
	    	int actionToTake = reactionPattern(estimation1,data);
	    	
	    	System.out.println("the value of the actionToTake " + actionToTake);
	    	solution.modifyCounter(actionToTake, true);
		}
		// only having a ceiling/floor value is not enough to make deductions about the stock market as there is no good comparison scale (no way to know if we are far or close to the ceiling/floor)
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
