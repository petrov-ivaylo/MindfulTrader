package com.mindfultrader.webapp.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mindfultrader.webapp.models.Roles;
import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.repositories.UserRepository;
import com.mindfultrader.webapp.services.CustomUserDetails;

 
@Controller
public class AppController {
 
    @Autowired
    private UserRepository userRepo;
     
    @GetMapping("/home")
    public String viewHomePage() {
        return "index";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
         
        return "signup_form";
    }
    
    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
         
        //userRepo.save(user);
        
        Roles role = new Roles();
        role.setName("USER");
        role.setId(user.getId());
        Set<Roles> roles = new HashSet();
        roles.add(role);
        user.setRoles(roles);
        
        userRepo.save(user);
        //rolesRepo.save(role);
        
         
        return "register_success";
    }
    
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
         
        return "users";
    }
    
    @GetMapping("/theory")
    public String strategy(Model model) {
    	return "theory";
    }
    
    @GetMapping("/subscription")
    public String subscription() {
    	return "subscription";
    }
    
    @RequestMapping(value="/subscription1", method=RequestMethod.POST)
    public String subscription1(@AuthenticationPrincipal CustomUserDetails principal) {
    	
    	User user = userRepo.findByEmail(principal.getUsername());
    	
    	Roles role = new Roles();
        role.setName("SUBSCRIBER1");
        role.setId(user.getId());
        Set<Roles> roles = new HashSet();
        roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        
        userRepo.save(user);
    	return "users";
    }
    
    @GetMapping("/subscription2")
    public String subscription2() {
    	return "subscription2";
    }
    
    @GetMapping("/account")
    public String editAccount()
    {
    	return "accountManagement/account";
    }
    
    @GetMapping("/403")
    public String accessDenied()
    {
    	return "403";
    }
}