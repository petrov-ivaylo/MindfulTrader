package com.mindfultrader.webapp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mindfultrader.webapp.algorithm.Algorithm;
import com.mindfultrader.webapp.algorithm.RequestData;
import com.mindfultrader.webapp.models.Company;
import com.mindfultrader.webapp.repositories.CompanyRepository;






@Controller
public class AlgorithmController {
	
	@Autowired
	private CompanyRepository companyRepo;
	
	@RequestMapping("/algorithm/run")
	public ModelAndView run(@RequestParam("cmp_id") Company cmp)
	{
		
		String symbol = cmp.getCompanySymbol();
		
        double [][] data = RequestData.dataRequest(symbol);
		
		
		
		System.out.println("Creating algorithm objects...");
		Algorithm algo1 = new Algorithm(data);
		
		int[] torun = {1,2,3,4,5};
		
		
		algo1.runAlgo(torun);
		System.out.println("Algo1 run.");
		System.out.println(algo1.solution.getListOfResults());
		System.out.println(algo1.solution.getFinalAdvice());
		System.out.println(data[0][89]);
		
		
		
		
		
		
		//Create MVC object for webapp
		ModelAndView mv = new ModelAndView();
		mv.setViewName("algoresult");
		mv.addObject("company_name", cmp.getCompanyName());
		mv.addObject("conclusion1", algo1.solution.getFinalAdvice());
		mv.addObject("advice1", algo1.solution.getListOfResults());
		
		
		return mv;
	}
	
	@RequestMapping("/algorithm")
	public String home()
	{
		System.out.println("\n\n\nHI!!!! I'M HERE!!!! LALALLALALALALLAL\n\n\n");
		return "algorithm";
	}
}
