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

@Controller
public class HomePageController {
	
	@Autowired
    private UserRepository userRepo;
    
    @Autowired
	private RolesRepository rolesRepo;

	@GetMapping("/home")
    public String viewHomePage() {
        return "index";
    }
	
	@GetMapping("/homee")
    public String listUsers(
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
	
	@GetMapping("/theory")
    public String strategy(Model model) {
    	return "theory";
    }
    
    @GetMapping("/subscription")
    public String subscription() {
    	return "subscription";
    }
    
    @GetMapping("/account")
    public String editAccount()
    {
    	return "accountManagement/account";
    }
    
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
