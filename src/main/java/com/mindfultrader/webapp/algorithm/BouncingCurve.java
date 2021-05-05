package com.mindfultrader.webapp.algorithm;

public class BouncingCurve {
	
	static class Limits{
		double ceiling = 0;
		double floor   = 0;
	}

	//This is a subfunction from the next Analysis Function. I'd advice you to look at the next one first to understand everything.
	//We look at the small scale summits and floors. We use this information to determine the 'safety net' and ceiling of the stock values. According advice not yet implemented, here I only they if there is such ceiling or floor.
	
	
	public static Limits bouncing1 (double[][] data){
		int[] indexsummits = new int[90];
		int[] indexlow = new int[90];
		int nbsummits = 0;
		int nblow = 0;
		Limits limit = new Limits();
		 
		//I created variables, a list of the days where I found a summit/floor and their number. I only keep 10 values here (and use only three) 'cause I will add different scales again.
		 
		
		 
		 
		//That's Hell you're walking into.
		for (int i =2; i<data[0].length - 2; i++){
	    	// I check everyday if its neighbours (two days before, two days after) are superior or inferior. If it is superior/inferior to all of them then it is listed as a summit/low.
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
    	//done
		 
		//If I found enough summits/low I compare them, and if I find that they have less than 10% of difference in value I consider this a floor/ceiling
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
		 
		// I return the values of a potential floor or ceiling.
		 
		return limit;
		 
	}
	
	public static int reactionPattern (Limits estimation, double[][]data){
	        int length = data[1].length;

	        //if values are strange, 0 is returned immediately
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
		 
		 
		if (estimation1.ceiling != 0 && estimation1.floor != 0){
	    	
			String advice = "Recurring pattern found. Ceiling found at " + estimation1.ceiling + " and floor at " + estimation1.floor;
			solution.addResultToList(advice);
	    	
	    	int actionToTake = reactionPattern(estimation1,data);
	    	
	    	System.out.println("the value of the actionToTake " + actionToTake);
	    	solution.modifyCounter(actionToTake, true);
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
