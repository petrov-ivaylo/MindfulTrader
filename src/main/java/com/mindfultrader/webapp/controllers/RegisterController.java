package com.mindfultrader.webapp.controllers;

/*
 * Controller handling the registering process
 * 
 * Date: 14 May 2021
 * 
 * Author: team Golf 2020-2021 Aberdeen
 */

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mindfultrader.webapp.models.Roles;
import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.repositories.RolesRepository;
import com.mindfultrader.webapp.repositories.UserRepository;

@Controller
public class RegisterController {
	
	@Autowired
    private UserRepository userRepo;
    
    @Autowired
	private RolesRepository rolesRepo;
    
    //Repositories used in the process of email verification
    
    /*@Autowired
    private ConfirmationTokenRepository confirmationTokenRepo;
    
    @Autowired
    private EmailSenderService emailSenderService;
    
    @Autowired
    private UserServices service;*/

	@GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
         
        return "register";
    }
	
	@PostMapping("/register_success")
    public ModelAndView registerUser(ModelAndView modelAndView, User user) {
    	
    	User existingUser = userRepo.findByEmail(user.getEmail());
        if(existingUser != null)
        {
        	modelAndView.addObject("message","This email already exists!");
            modelAndView.setViewName("existenceError");
        }
        else
        {
        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            
        	Roles role = new Roles();
            Set<Roles> roles = user.getRoles();
            role = rolesRepo.findByname("USER");
            roles.add(role);
            user.setRoles(roles);
            //userRepo.save(user);
            userRepo.save(user);
            
            modelAndView.addObject("");
            modelAndView.setViewName("register_success");
        
        }
        return modelAndView;
    }
	
	//The next commented functions were used in the process of email verification
	    
    /*@PostMapping("/register_success")
    public String processRegister(User user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        service.register(user, getSiteURL(request));       
        return "register_success";
    }
    
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    } */
    
    /*@RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView, User user)
    {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }
    
    
    /*@GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (service.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }*/
	
}
