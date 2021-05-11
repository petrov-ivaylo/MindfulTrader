package com.mindfultrader.webapp.controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mindfultrader.webapp.algorithm.Algorithm;
import com.mindfultrader.webapp.algorithm.RequestData;
import com.mindfultrader.webapp.models.Company;
import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.models.WatchlistPortfolio;
import com.mindfultrader.webapp.repositories.CompanyRepository;
import com.mindfultrader.webapp.repositories.UserRepository;
import com.mindfultrader.webapp.repositories.WatchlistPortfolioRepository;
import com.mindfultrader.webapp.services.CustomUserDetails;

//Controller to handle the algorithm processes
@Controller
public class AlgorithmController {
	
	@Autowired
	private CompanyRepository companyRepo;
	
	@Autowired
	WatchlistPortfolioRepository wpRepo;
	
	@Autowired
	UserRepository userRepo;
	
	//Controller to run the algorithm on just one company
	@RequestMapping("/algorithm/run")
	public ModelAndView run(@RequestParam("cmp_id") Company cmp)
	{
		
		String symbol = cmp.getCompanySymbol();
		
        double [][] data = RequestData.dataRequest(symbol);
		
		
		
		System.out.println("Creating algorithm objects...");
		Algorithm algo1 = new Algorithm(data);
		
		int[] torun = {1,2,3,4,5};
		
		
		algo1.runAlgo(torun);		
		
		//Create MVC object for webapp
		ModelAndView mv = new ModelAndView();
		mv.setViewName("algoresult");
		mv.addObject("company_name", cmp.getCompanyName());
		mv.addObject("conclusion1", algo1.solution.getFinalAdvice());
		mv.addObject("advice1", algo1.solution.getListOfResults());
		
		
		return mv;
	}	
	
	//Controller to run the algorithm on all companies in the user's portfolio
	@RequestMapping(value="runAlgoPortfolio", method=RequestMethod.POST)
	public ModelAndView runAlgoPortfolio(@AuthenticationPrincipal CustomUserDetails principal)
	{
		List<String> company_names = new ArrayList<String>();
		List<String> conclusions1 = new ArrayList<String>();
		List<String> advices1 = new ArrayList<String>();
		
		User user = userRepo.findByEmail(principal.getUsername());
		List<WatchlistPortfolio> entry = wpRepo.findByUseridAndType(user.getId(), "p");
		
		for (int i=0;i<entry.size();i++) {
			
			Long id = entry.get(i).getCompanyid();
			Optional<Company> cmp = companyRepo.findById(id);
			String symbol = cmp.get().getCompanySymbol();
			
	        double [][] data = RequestData.dataRequest(symbol);
			
			Algorithm algo1 = new Algorithm(data);
			
			int[] torun = {1,2,3,4,5};
			
			
			algo1.runAlgo(torun);
			
			company_names.add("Algorithm result for company " + cmp.get().getCompanyName());
			conclusions1.add("Conclusion from algorithm is: " + algo1.solution.getFinalAdvice());
			advices1.add("Breakdown of advice is: " + algo1.solution.getListOfResults());

		}
		
		List<Integer> indices = new ArrayList<Integer>();
		for (int i=0; i<company_names.size();i++) {
        	indices.add(i);
		}
		
		//Create MVC object for webapp
		ModelAndView mv = new ModelAndView();
		mv.setViewName("algoresultMulti");
		mv.addObject("indices",indices);
		mv.addObject("company_names", company_names);
		mv.addObject("conclusions1", conclusions1);
		mv.addObject("advices1", advices1);
		
		
		return mv;
	}
	
	//Controller to run the algorithm on all companies in the user's watchlist
	@RequestMapping(value="/runAlgoWatchlist", method=RequestMethod.POST)
	public ModelAndView runAlgoWatchlist(@AuthenticationPrincipal CustomUserDetails principal)
	{
		List<String> company_names = new ArrayList<String>();
		List<String> conclusions1 = new ArrayList<String>();
		List<String> advices1 = new ArrayList<String>();
		
		User user = userRepo.findByEmail(principal.getUsername());
		List<WatchlistPortfolio> entry = wpRepo.findByUseridAndType(user.getId(), "w");
		
		for (int i=0;i<entry.size();i++) {
			
			Long id = entry.get(i).getCompanyid();
			Optional<Company> cmp = companyRepo.findById(id);
			String symbol = cmp.get().getCompanySymbol();
			
	        double [][] data = RequestData.dataRequest(symbol);
			
			Algorithm algo1 = new Algorithm(data);
			
			int[] torun = {1,2,3,4,5};
			
			
			algo1.runAlgo(torun);
			
			company_names.add("Algorithm result for company " + cmp.get().getCompanyName());
			conclusions1.add("Conclusion from algorithm is: " + algo1.solution.getFinalAdvice());
			advices1.add("Breakdown of advice is: " + algo1.solution.getListOfResults());

		}
		
		List<Integer> indices = new ArrayList<Integer>();
		for (int i=0; i<company_names.size();i++) {
        	indices.add(i);
		}
		
		//Create MVC object for webapp
		ModelAndView mv = new ModelAndView();
		mv.setViewName("algoresultMulti");
		mv.addObject("indices",indices);
		mv.addObject("company_names", company_names);
		mv.addObject("conclusions1", conclusions1);
		mv.addObject("advices1", advices1);
		
		
		return mv;
	}
	
}
