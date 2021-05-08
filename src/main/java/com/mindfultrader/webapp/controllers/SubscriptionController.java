package com.mindfultrader.webapp.controllers;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    	//return "users";
    }
    
    @GetMapping("/subscription2")
    public String subscription2() {
    	return "subscription2";
    }
	
}
