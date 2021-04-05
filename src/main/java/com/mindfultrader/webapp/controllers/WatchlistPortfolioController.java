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
	
	
	//Controller to handle requests to move company from master companies list to watchlist
	@RequestMapping(value="/addToWatchlist", method=RequestMethod.POST)
	public String addToWatchlist(
			@AuthenticationPrincipal CustomUserDetails principal,
			@RequestParam("Company_ID") Company company)
	{
		
		/* Note: we are sending the company id through from the view, but behind the scenes the server is using the id to look up the company through the repo, meaning we get an object 
		 * of type company. In thymeleaf the variables and other stuff get represented as strings, so if we try to send the company through instead of the id, we get a conversion error
		 * because it doesn't know how to convert the string representing the company object in the view to an actual company object in the model.
		 */

		//get current user
		User user = userRepo.findByEmail(principal.getUsername());
		
		//create new watchlist object and set fields according to received company, then save to database via repository - remember list id automatically generated when created
		WatchlistPortfolio wpEntry = new WatchlistPortfolio();
		
		wpEntry.setCompanyid(company.getCompany_ID());
		wpEntry.setUserid(user.getId());
		wpEntry.setType("w");
		
		wpRepo.save(wpEntry);
		
		System.out.println(company.getCompanyName() + " added to watchlist");
		
		//reload the page - you will see the company on the watchlist
		return "redirect:/portfolio_watchlist";
	}
	
	
	//Controller to handle requests to move company from master companies list to portfolio - again repeated code, can we move to service where we simply add the company, user? and the type as params?
	@RequestMapping(value="/addToPortfolio", method=RequestMethod.POST)
	public String addToPortfolio(
			@AuthenticationPrincipal CustomUserDetails principal,
			@RequestParam("Company_ID") Company company)
	{
		
		/* Note: we are sending the company id through from the view, but behind the scenes the server is using the id to look up the company through the repo, meaning we get an object 
		 * of type company. In thymeleaf the variables and other stuff get represented as strings, so if we try to send the company through instead of the id, we get a conversion error
		 * because it doesn't know how to convert the string representing the company object in the view to an actual company object in the model.
		 */

		//get current user
		User user = userRepo.findByEmail(principal.getUsername());
		
		//create new watchlist object and set fields according to received company, then save to database via repository - remember list id automatically generated when created
		WatchlistPortfolio wpEntry = new WatchlistPortfolio();
		
		wpEntry.setCompanyid(company.getCompany_ID());
		wpEntry.setUserid(user.getId());
		wpEntry.setType("p");
		
		wpRepo.save(wpEntry);
		
		System.out.println(company.getCompanyName() + " added to portfolio");
		
		//reload the page - you will see the company on the watchlist
		return "redirect:/portfolio_watchlist";
	}
	
	//Controller to handle requests to delete entry from either portfolio or watchlist. 
	@RequestMapping(value="/deleteFromWP", method=RequestMethod.POST)
	public String deteleFromWP(
			@AuthenticationPrincipal CustomUserDetails principal,
			@RequestParam("Company_ID") Company company)
	{
		User user = userRepo.findByEmail(principal.getUsername());
		
		//find the entry in the WatchlistPortfolio table that we want to delete
		List<WatchlistPortfolio> entry = wpRepo.findByUseridAndCompanyid(user.getId(), company.getCompany_ID());
		
		
		//delete the entry - deletes all entries in both watchlist and portfolio. Once implemented so company can only be on one, change to wpRepo.delete(entry) and change 
		// repository return type to single entry.
		wpRepo.deleteAll(entry);
		
		System.out.println(company.getCompanyName() + " deleted from portfolio and/or watchlist");
		
		return "redirect:/portfolio_watchlist";
	}
	
	//Controller to handle request to move entry from watchlist to portfolio
	@RequestMapping(value="/moveWtoP", method=RequestMethod.POST)
	public String moveWtoP(
			@AuthenticationPrincipal CustomUserDetails principal,
			@RequestParam("Company_ID") Company company
			)
	{
		User user = userRepo.findByEmail(principal.getUsername());
		
		//find the entry in the WatchlistPortfolio table that we want to delete - remove cast when condition to only have in either watchlist or portfolio
		WatchlistPortfolio entry = wpRepo.findByUseridAndCompanyidAndType(user.getId(), company.getCompany_ID(), "w");
		
		//Change type and save entry back to db
		entry.setType("p");
		wpRepo.save(entry);
		
		return "redirect:/portfolio_watchlist";
	}
	
	
	//Controller to handle request to move entry from portfolio to watchlist
	@RequestMapping(value="/movePtoW", method=RequestMethod.POST)
	public String movePtoW(
			@AuthenticationPrincipal CustomUserDetails principal,
			@RequestParam("Company_ID") Company company
			)
	{
		User user = userRepo.findByEmail(principal.getUsername());
		
		//find the entry in the WatchlistPortfolio table that we want to delete - remove cast when condition to only have in either watchlist or portfolio
		WatchlistPortfolio entry = wpRepo.findByUseridAndCompanyidAndType(user.getId(), company.getCompany_ID(), "p");
		
		//Change type and save entry back to db
		entry.setType("w");
		wpRepo.save(entry);
		
		return "redirect:/portfolio_watchlist";
	}
	
}
