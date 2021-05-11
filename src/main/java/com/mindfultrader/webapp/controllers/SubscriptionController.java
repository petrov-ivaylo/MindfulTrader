package com.mindfultrader.webapp.controllers;

/*
 * Controller handling the process of subscription - our users should be subscribed in order to use our service
 * 
 * Date: 14 May 2021
 * 
 * Author: team Golf 2020-2021 Aberdeen
 */

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mindfultrader.webapp.models.Roles;
import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.repositories.RolesRepository;
import com.mindfultrader.webapp.repositories.UserRepository;
import com.mindfultrader.webapp.services.CustomUserDetails;

@Controller
public class SubscriptionController {
	
	@Autowired
    private UserRepository userRepo;
    
    @Autowired
	private RolesRepository rolesRepo;

	@RequestMapping(value="/subscription1", method=RequestMethod.POST)
    public String subscription1(@AuthenticationPrincipal CustomUserDetails principal,
			HttpServletRequest request, 
			HttpServletResponse response) {
    	
    	User user = userRepo.findByEmail(principal.getUsername());
    	Roles role = new Roles();
        Set<Roles> roles = user.getRoles();
        role = rolesRepo.findByname("SUBSCRIBER1");
    	
        roles.add(role);
        user.setRoles(roles);
        
        userRepo.save(user);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth); 
		}
		
		return "subscriptionConfirmation";
    }
    
	//We plan to have different types of subscriptions in the future
    /*@GetMapping("/subscription2")
    public String subscription2() {
    	return "subscription2";
    }*/
	
}
