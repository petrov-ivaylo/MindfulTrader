package com.mindfultrader.webapp.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mindfultrader.webapp.models.Roles;
import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.models.WatchlistPortfolio;
import com.mindfultrader.webapp.repositories.UserRepository;
import com.mindfultrader.webapp.repositories.WatchlistPortfolioRepository;

@Controller
public class ListUsersController {

	@Autowired
    private UserRepository userRepo;
	
	@Autowired
    private WatchlistPortfolioRepository wpRepo;
    
	@RequestMapping(value="/deleteUser", method=RequestMethod.POST)
	public String deleteUserFromDB(@RequestParam("user") User user){
		
		Set<Roles> roles = new HashSet<>();
		user.setRoles(roles);
		//find all the entries in the WatchlistPortfolio table that we want to delete - the ones connected with the user to be deleted
		List<WatchlistPortfolio> entry = wpRepo.findByUserid(user.getId());
		wpRepo.deleteAll(entry);
		//After clearing the database from the user's records in the watchlist/portfolio, then simply delete their account
		userRepo.delete(user);
		
		return "redirect:/listUsers";
	}
}
