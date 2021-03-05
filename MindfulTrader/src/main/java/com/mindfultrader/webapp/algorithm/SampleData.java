package com.mindfultrader.webapp.algorithm;

/*
 * Class to create sample data object. Will be replaced/removed when we start pulling data from database
 */


import org.springframework.stereotype.Component;

@Component
public class SampleData {
			
		double[]   opens1  = {100.0 , 100.0 , 100.0 , 100.0};
		double[]   maxs1   = {100.0 , 100.0 , 100.0 , 200.0};
		double[]   mins1   = {100.0 , 100.0 , 100.0 , 80.0 };
		double[]   closes1 = {100.0 , 100.0 , 100.0 , 90.0 };
		
		public 	double[][] data1   = {opens1,maxs1,mins1,closes1};

		double[]   opens2  = {10.0 , 12.0 , 14.0 , 8.0};
		double[]   maxs2   = { 1.0 ,  1.0 , 10.0 , 1.0};
		double[]   mins2   = { 1.0 ,  1.0 ,  1.0 , 1.0};
		double[]   closes2 = { 1.0 ,  1.0 ,  1.0 , 1.0};
		
		public double[][] data2   = {opens2,maxs2,mins2,closes2};

		double[]   opens3  = {10.0 , 11.0 , 13.0 , 17.0 , 25.0 , 41.0};
		double[]   maxs3   = {10.0 , 10.0 , 10.0 , 10.0 , 10.0 , 10.0};
		double[]   mins3   = {10.0 , 10.0 , 10.0 , 10.0 , 10.0 , 10.0};
		double[]   closes3 = {10.0 , 10.0 , 10.0 , 10.0 , 10.0 , 10.0};
		
		public double[][] data3   = {opens3,maxs3,mins3,closes3};
		
		public int[] torun = {1,2};
	
	
	
	
	
}
