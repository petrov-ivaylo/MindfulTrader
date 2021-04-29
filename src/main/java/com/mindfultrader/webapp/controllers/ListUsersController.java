package com.mindfultrader.webapp.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mindfultrader.webapp.models.Roles;
import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.repositories.UserRepository;

@Controller
public class ListUsersController {

	@Autowired
    private UserRepository userRepo;
	
	//@Autowired
    //private UsersRolesRepository users_rolesRepo;
    
	@RequestMapping(value="/deleteUser", method=RequestMethod.POST)
	public String deleteCompanyFromDB(@RequestParam("user") User user){
		
		//Long id = user.getId();
		Set<Roles> roles = new HashSet<>();
		user.setRoles(roles);
		userRepo.delete(user);
		//List<UsersRoles> entry = users_rolesRepo.findByUserid(id);
		
		//users_rolesRepo.deleteAll(entry);
		//userRepo.delete(user);
		
		return "redirect:/listUsers";
	}
}
