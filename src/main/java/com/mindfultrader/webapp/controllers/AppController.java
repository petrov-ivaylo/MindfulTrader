package com.mindfultrader.webapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.repositories.UserRepository;

 
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
         
        userRepo.save(user);
         
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
    
//	@RequestMapping("/users/run")
//	public ModelAndView run()
//	{
//		
//		//Run algorithm and print to console
//		System.out.println("Creating Data object...");
//		SampleData sdata = new SampleData();
//		
//		
//		System.out.println("Creating algorithm objects...");
//		Algorithm algo1 = new Algorithm(sdata.data1);
//		Algorithm algo2 = new Algorithm(sdata.data2);
//		Algorithm algo3 = new Algorithm(sdata.data3);
//		
//		algo1.runAlgo(sdata.torun);
//		System.out.println("Algo1 run.");
//		System.out.println(algo1.solution.getListOfResults());
//		System.out.println(algo1.solution.getFinalAdvice());
//		
//		algo2.runAlgo(sdata.torun);
//		System.out.println("Algo2 run.");
//		System.out.println(algo2.solution.getListOfResults());
//		System.out.println(algo2.solution.getFinalAdvice());
//
//		algo3.runAlgo(sdata.torun);
//		System.out.println("Algo3 run.");
//		System.out.println(algo3.solution.getListOfResults());
//		System.out.println(algo3.solution.getFinalAdvice());
//		
//		
//		System.out.println("Algorithm has run :) ");
//		
//		
//		//Create MVC object for webapp
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("result");
//		mv.addObject("conclusion", algo1.solution.getFinalAdvice());
//		mv.addObject("advice", algo1.solution.getListOfResults());
//		
//		
//		//Return to previous Page
//		return mv;
//	}
//    
}