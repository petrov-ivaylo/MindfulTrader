package com.mindfultrader.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mindfultrader.webapp.models.Company;
import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.models.WatchlistPortfolio;
import com.mindfultrader.webapp.repositories.CompanyRepository;
import com.mindfultrader.webapp.repositories.UserRepository;
import com.mindfultrader.webapp.repositories.WatchlistPortfolioRepository;
import com.mindfultrader.webapp.services.CustomUserDetails;

/*
 * Controller used for Watchlist-Portfolio page
 * 	
 * 
 * 	User must be able to view their portfolio
 * 	User must be able to view their watchlist
 *  User must be able to add and remove companies from an admin defined list of options to/from their portfolio
 *  User must be able to add and remove companies from an admin defined list of options to/from their watchlist
 * 
 */

@Controller
public class WatchlistPortfolioController {

	@Autowired
	WatchlistPortfolioRepository wpRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CompanyRepository companyRepo;
	
	//Main page for portfolio watchlist management
	@RequestMapping("/portfolio_watchlist")
	public String viewPortWatch(
			@AuthenticationPrincipal CustomUserDetails principal,
			Model model)
	{	
		
		//Pull current user from database by using spring security principal object to search in user repository
		User user = userRepo.findByEmail(principal.getUsername());
		
		/*
		 * **********************************************************************
		 * GET PORTFOLIO 
		 * **********************************************************************
		 */
		//Pull a list of all entries in the portfolio for user
		List<WatchlistPortfolio> portfolio_temp = wpRepo.findByUseridAndType(user.getId(), "p");
		
		
		//Access company ids in portfolio
		List<Long> companyids_p = new ArrayList<Long>();
		for (WatchlistPortfolio entry : portfolio_temp)
		{
			companyids_p.add(entry.getCompanyid());
		}
		
		//pull companies in portfolio
		//Lookup companies by id
		List<Company> portfolio = companyRepo.findAllById(companyids_p);
		
		//Add portfolio as list of company objects to model
		model.addAttribute("portfolio", portfolio);
		
		
		
		
		
		/*
		 * **********************************************************************
		 * GET WATCHLIST
		 * **********************************************************************
		 * REPEATED CODE - should be doable by creating a service function... interface? or Bean?
		 */
		//Pull a list of all entries in the watchlist for user
		List<WatchlistPortfolio> watchlist_temp = wpRepo.findByUseridAndType(user.getId(), "w");
		
		
		//Access company ids in watchlist
		List<Long> companyids_w = new ArrayList<Long>();
		for (WatchlistPortfolio entry : watchlist_temp)
		{
			companyids_w.add(entry.getCompanyid());
		}
		
		//pull companies in watchlist
		//Lookup companies by id
		List<Company> watchlist = companyRepo.findAllById(companyids_w);
		
		//Add watchlist as list of company objects to model
		model.addAttribute("watchlist", watchlist);
		
		
		
		/*
		 * ***************************************************************************
		 * Get All companies
		 * ***************************************************************************
		 */
		
		//Pull all companies in company table into list
		List<Company> allCompanies = companyRepo.findAll();
		
		//Add allCompanies to model so it is accessible in view
		model.addAttribute("allCompanies", allCompanies);
		
		return "portfolio_watchlist";
	}
	
	@RequestMapping(value="/addToWatchlist", method=RequestMethod.POST)
	public String addToWatchlist(
			@RequestParam("Company_ID") Company company)
	{
		
		//Note: we are sending the company id through from the view, but behind the scenes the server is using the id to look up the company through the repo, meaning we get an object 
		// of type company. In thymeleaf the variables and other stuff get represented as strings, so if we try to send the company through instead of the id, we get a conversion error
		// because it doesn't know how to convert the string representing the company object in the view to an actual company object in the model.
		
		System.out.println(company.getCompanyName());
		
		return "redirect:/portfolio_watchlist";
	}
	
}
