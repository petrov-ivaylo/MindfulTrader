package com.mindfultrader.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mindfultrader.webapp.models.Company;
import com.mindfultrader.webapp.models.Roles;
import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.repositories.CompanyRepository;
import com.mindfultrader.webapp.repositories.RolesRepository;
import com.mindfultrader.webapp.repositories.UserRepository;
import com.mindfultrader.webapp.services.CustomUserDetails;

//Controller to handle the processes available for admins only - all differences between admins and users are available at the home page
@Controller
public class AdminHomePageController {
	
	@Autowired
    private UserRepository userRepo;
    
    @Autowired
	private RolesRepository rolesRepo;
    
    @Autowired
	private CompanyRepository companyRepo;
    
    //Admins can add and/or delete companies from the database
	@GetMapping("/changeCompanies")
    public String chnageCompanies(Model model) {
    	List<Company> companies = companyRepo.findAll();
    	model.addAttribute("allCompanies", companies);
    	return "changeCompanies";
    }
    
	//Admins have access to all users in the system and can delete profiles
    @GetMapping("/listUsers")
    public String list_users(@AuthenticationPrincipal CustomUserDetails principal,
    		Model model) {
    	List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        List<Integer> indices = new ArrayList<Integer>();

        List<String> sub = new ArrayList<String>();
        for (int i=0; i<listUsers.size();i++) {
        	
        	indices.add(i);
        	
        	User user = listUsers.get(i);
            Roles role = new Roles();
            
            role = rolesRepo.findByname("SUBSCRIBER1");
            
            if(user.getRoles().contains(role)) {
            	sub.add("YES");
            }
            else {
            	sub.add("NO");
            }
        }
        model.addAttribute("indices", indices);

        model.addAttribute("subscriber",sub);
    	return "listUsers";
    }
	
}
