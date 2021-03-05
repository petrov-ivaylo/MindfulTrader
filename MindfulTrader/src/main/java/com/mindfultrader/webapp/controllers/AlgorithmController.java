package com.mindfultrader.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mindfultrader.webapp.algorithm.Algorithm;
import com.mindfultrader.webapp.algorithm.SampleData;

@Controller
public class AlgorithmController {
	
	@RequestMapping("/algorithm/run")
	public ModelAndView run()
	{
		
		//Run algorithm and print to console
		System.out.println("Creating Data object...");
		SampleData sdata = new SampleData();
		
		
		System.out.println("Creating algorithm objects...");
		Algorithm algo1 = new Algorithm(sdata.data1);
		Algorithm algo2 = new Algorithm(sdata.data2);
		Algorithm algo3 = new Algorithm(sdata.data3);
		
		algo1.runAlgo(sdata.torun);
		System.out.println("Algo1 run.");
		System.out.println(algo1.solution.getListOfResults());
		System.out.println(algo1.solution.getFinalAdvice());
		
		algo2.runAlgo(sdata.torun);
		System.out.println("Algo2 run.");
		System.out.println(algo2.solution.getListOfResults());
		System.out.println(algo2.solution.getFinalAdvice());

		algo3.runAlgo(sdata.torun);
		System.out.println("Algo3 run.");
		System.out.println(algo3.solution.getListOfResults());
		System.out.println(algo3.solution.getFinalAdvice());
		
		
		System.out.println("Algorithm has run :) ");
		
		
		//Create MVC object for webapp
		ModelAndView mv = new ModelAndView();
		mv.setViewName("algoresult");
		mv.addObject("conclusion1", algo1.solution.getFinalAdvice());
		mv.addObject("advice1", algo1.solution.getListOfResults());
		
		mv.addObject("conclusion2", algo2.solution.getFinalAdvice());
		mv.addObject("advice2", algo2.solution.getListOfResults());
		
		mv.addObject("conclusion3", algo3.solution.getFinalAdvice());
		mv.addObject("advice3", algo3.solution.getListOfResults());
		
		return mv;
	}
	
	@RequestMapping("/algorithm")
	public String home()
	{
		System.out.println("\n\n\nHI!!!! I'M HERE!!!! LALALLALALALALLAL\n\n\n");
		return "algorithm";
	}
}
