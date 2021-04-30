package com.mindfultrader.webapp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.repositories.UserRepository;
import com.mindfultrader.webapp.services.CustomUserDetails;

/*
 * Controller for user account management functionality
 * 
 * User must be able to delete their account
 * User must be able to change their email
 * User must be able to change their password
 * 
 * Tutorials used: 
 * https://www.codejava.net/frameworks/spring-boot/get-logged-in-user-details
 * 
 * Created: 24 Mar 2021
 * Updated 27 Apr 2021
 * 
 * 
 * BB Tested 27-04-2021
 * 		User cannot change email to an email already existing in db
 * 		User cannot change email with mismatching confirmation entry
 * 
 * For future development:
 * 		Email must be a valid email address
 * 		Password must be above some length
 */

@Controller
public class EditAccountController {
	
	@Autowired
	UserRepository userRepo;
	
	// Main page for account edits
	@RequestMapping("/account")
	public String editAccount()
	{
		
		return "accountManagement/account";
	}
	
	
	// On first click of delete, user gets warning message for account deletion
	@RequestMapping("/account/delete")
	public String confirmAccountDeletion()
	{
		return "accountManagement/delete_account";
	}
	
	
	// Delete account and all details
	@RequestMapping("/account/delete/submit")
	public String deleteAccount(
			@AuthenticationPrincipal CustomUserDetails principal, 
			HttpServletRequest request, 
			HttpServletResponse response)	
	{
		//Find user and delete using repository
		User user = userRepo.findByEmail(principal.getUsername());
		userRepo.delete(user);
		
		//Ensure we are logged in and then log out
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);  
        }  
		
		return "accountManagement/bye";
	}
		

	
	// Edit email request
	@RequestMapping(value="/account/editEmail", method=RequestMethod.POST)
	public ModelAndView processEmail(
			@AuthenticationPrincipal CustomUserDetails principal, 
			@RequestParam("email") String email, 
			@RequestParam("email_confirm") String email_confirm,
			HttpServletRequest request, 
			HttpServletResponse response,
			ModelAndView mv)
	{
		/* Accept information sent by user via POST request, by submitting form in account.html
		 * page. 
		 * Check that the two emails match, if not redirect to error page.
		 * Update user info
		 * Return confirmation page 
		 * 
		 * 
		 * https://spring.io/guides/gs/handling-form-submission/
		 * https://www.codejava.net/frameworks/spring-boot/spring-boot-thymeleaf-form-handling-tutorial
		 * 
		 * https://stackoverflow.com/questions/24671221/unable-to-get-thymeleaf-form-data-in-spring-mvc
		 */
		
		
		//Pull user from database
		User user = userRepo.findByEmail(principal.getUsername());
		
		//Inputted emails should not be case sensitive, nor do we want any whitespace to mess things up
		email = email.strip().toLowerCase();
		email_confirm = email.strip().toLowerCase();
		
		if (email.contains("@") != true) {
			// make sure email format is valid - spring checks for @ automatically, however not for a empty entry
			mv.addObject("message", "The email was not valid. Please enter a valid email.");
			mv.setViewName("accountManagement/error");
		}
		else if (userRepo.existsUserByEmail(email) != false) {
			// email must not already exist in system - includes case where user inputs their current email
			mv.addObject("message", "This user email already exists. Please enter another email.");
			mv.setViewName("accountManagement/error");
		}
		else if (email.equals(email_confirm)) { //use email.equalsIgnoreCase(email_confirm) if we do not care about case.
			//Change user email to value received in PUSH request
			user.setEmail(email);
			
			//Save user to database
			userRepo.save(user);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null){
				new SecurityContextLogoutHandler().logout(request, response, auth);  
	        }  
			
			//Return confirmation page
			mv.addObject("email", email);
			mv.setViewName("accountManagement/emailConfirmation");
			
			// FOR TESTING PURPOSES
			System.out.println("\n\nEditAccountController: Email Updated!\n\n");
		}
		else {
			mv.addObject("message", "The emails did not match! Please try again.");
			mv.setViewName("accountManagement/error");
		}
		
		return mv;

	}
	
	
	// Edit password
	@RequestMapping(value="/account/changePassword", method=RequestMethod.POST)
	public ModelAndView editPassword(
			@AuthenticationPrincipal CustomUserDetails principal,
			@RequestParam("old_password") String old_password,
			@RequestParam("password") String password,
			@RequestParam("confirm_password") String confirm_password,
			HttpServletRequest request, 
			HttpServletResponse response,
			ModelAndView mv)
	{
		
		//pull user from db and get a password encoder object
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = userRepo.findByEmail(principal.getUsername());
		
		//Access user password in database
		String dbPassword = user.getPassword();
		
		if (password.equals(confirm_password) != true) {
			mv.addObject("message", "The new passwords did not match. Please try again.");
			mv.setViewName("accountManagement/error");
		}
		else if (passwordEncoder.matches(old_password, dbPassword) != true) {
			mv.addObject("message", "Incorrect password. Please make sure to enter your current password in the first box.");
			mv.setViewName("accountManagement/error");
		}  
			
		else {
		    // Report error on error page
		    // Encode new password and store it
			user.setPassword(passwordEncoder.encode(password));
			userRepo.save(user);
			
			// return confirmation page	
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null){
				new SecurityContextLogoutHandler().logout(request, response, auth); 
			}
			
			mv.setViewName("accountManagement/passwordConfirmation");
			
			// FOR TESTING PURPOSES
			System.out.println("\n\nEditAccountController: Password Updated!\n\n");
		}
		
		return mv;
	}

	
	
}
