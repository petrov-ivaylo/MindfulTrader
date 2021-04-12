package com.mindfultrader.webapp.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mindfultrader.webapp.models.ConfirmationToken;
import com.mindfultrader.webapp.models.Roles;
import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.repositories.ConfirmationTokenRepository;
import com.mindfultrader.webapp.repositories.RolesRepository;
import com.mindfultrader.webapp.repositories.UserRepository;
import com.mindfultrader.webapp.services.CustomUserDetails;
import com.mindfultrader.webapp.services.EmailSenderService;

 
@Controller
public class AppController {
 
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
	private RolesRepository rolesRepo;
    
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepo;
    
    @Autowired
    private EmailSenderService emailSenderService;

     
    @GetMapping("/home")
    public String viewHomePage() {
        return "index";
    }
    
    /*@GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
         
        return "signup_form";
    }*/
    
    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView, User user)
    {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }
    
    @RequestMapping(value="/register", method = RequestMethod.POST)
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

            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            
            System.out.println(user.getFirstName());
            System.out.println(confirmationToken.getUser().getId());

            
            confirmationTokenRepo.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("mindfultraderproject@gmail.com");
            mailMessage.setText("To confirm your account, please click here: "
            +"http://mindful-trader.herokuapp.com/confirm-account?token="+confirmationToken.getConfirmationToken());

            //emailSenderService.sendEmail(mailMessage);
            
            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("email", user.getEmail());

            modelAndView.setViewName("successfulRegisteration");
        
        //return "register_success";
        }
        return modelAndView;
    }
    
    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepo.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userRepo.findByEmail(token.getUser().getEmail());
            user.setEnabled(true);
            userRepo.save(user);
            modelAndView.setViewName("accountVerified");
        }
        else
        {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("existenceError");
        }

        return modelAndView;
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
        Set<Roles> roles = user.getRoles();
        role = rolesRepo.findByname("SUBSCRIBER1");
    	
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