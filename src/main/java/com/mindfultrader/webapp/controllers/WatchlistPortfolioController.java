package com.mindfultrader.webapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.models.WatchlistPortfolio;
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
	
	//Main page for portfolio watchlist management
	@RequestMapping("/portfolio_watchlist")
	public String viewPortWatch(
			@AuthenticationPrincipal CustomUserDetails principal,
			Model model)
	{	
		//Pull current user from database by using spring security principal object to search in user repository
		User user = userRepo.findByEmail(principal.getUsername());
		
		//Pull a list of all entries in the portfolio for user
		List<WatchlistPortfolio> portfolio = wpRepo.findByUserIDAndType(user.getId(), 'p');
		
		//Add portfolio (list of WatchlistPortfolio objects) to model
		model.addAttribute("portfolio", portfolio);
		
		
		return "portfolio_watchlist";
	}
	
}
