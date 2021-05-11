package com.mindfultrader.webapp.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mindfultrader.webapp.models.Roles;
import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.repositories.RolesRepository;
import com.mindfultrader.webapp.repositories.UserRepository;
import com.mindfultrader.webapp.services.CustomUserDetails;

//Controller to handle the requests happening in the main (home) page of the application
@Controller
public class HomePageController {
	
	@Autowired
    private UserRepository userRepo;
    
    @Autowired
	private RolesRepository rolesRepo;

    //The index page (first page the user sees)
	@GetMapping("/home")
    public String viewHomePage() {
        return "index";
    }
	
	//The home page of the application, after authenticating a user
	@GetMapping("/homee")
    public String homePage(
    		@AuthenticationPrincipal CustomUserDetails principal) {
		
        User user = userRepo.findByEmail(principal.getUsername());
        Roles role = new Roles();
        
        
        role = rolesRepo.findByname("ADMIN");
        
        if(user.getRoles().contains(role)) {
        	return "homeAdmin";
        }
        else {
        	return "home";
        }
        
    }
	
	//The url to our theory page which describes the methods we are using in our algorithm
	@GetMapping("/theory")
    public String strategy(Model model) {
    	return "theory";
    }
    
	//Every customer should be a subscriber in order to use our service
    @GetMapping("/subscription")
    public String subscription() {
    	return "subscription";
    }
    
    //The link leading to your personal account
    @GetMapping("/account")
    public String editAccount()
    {
    	return "accountManagement/account";
    }
    
    //The error page shown, when you try to access a page not available for you
    @GetMapping("/403")
    public String accessDenied(@AuthenticationPrincipal CustomUserDetails principal) {
    	User user = userRepo.findByEmail(principal.getUsername());
    	Roles role = new Roles();
    	role = rolesRepo.findByname("SUBSCRIBER1");
    	Set<Roles> user_roles = user.getRoles();
    
    	if(!user_roles.contains(role)) {
    		return "403";
    	}
    	return "adminAccessOnly";
    	
    }
	
}
