package com.mindfultrader.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;

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
 */


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.repositories.UserRepository;
import com.mindfultrader.webapp.services.CustomUserDetails;



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
	
	
	// Warning message for account deletion
	@RequestMapping("/account/delete")
	public String confirmAccountDeletion()
	{
		return "accountManagement/delete_account";
	}
	
	
	// Delete account and all details
	@RequestMapping("/account/delete/submit")
	public String deleteAccount()	
	{
		//CODE HERE
		
		return "accountManagement/bye";
	}
	
//	// Edit email
//	@RequestMapping(value="/account/editEmail", method=RequestMethod.GET)
//	public String editEmail(@AuthenticationPrincipal CustomUserDetails user, Model model)
//	{
//		// html page accesses user info directly using thymeleaf extra for Spring Security, 
//		// so only purpose of this is to give client side the correct html page.. which we do from 
//		// account so no purpose of this controller?
//		return "accountManagement/change_email";
//	}
	
	@RequestMapping(value="/account/editEmail", method=RequestMethod.POST)
	public String processEmail(
			@AuthenticationPrincipal CustomUserDetails principal, 
			@RequestParam("email") String email, 
			@RequestParam("email_confirm") String email_confirm,
			Model model)
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
		
		System.out.println("printing...." + email + " and again: " + email_confirm);
		
		//Pull user from database
		User user = userRepo.findByEmail(principal.getUsername());
		
		System.out.println(user.getEmail());
		
		//Change user email to value received in PUSH request
		user.setEmail(email);
		
		System.out.println("New email: " + user.getEmail());
		
		//Save user to database
		userRepo.save(user);
		
		//Return confirmation page
		return "accountManagement/email_confirmation";
		
//		THE PAIN OF ATTEMPTING TO TEST BEFORE WE CHANGE!
//		
//		email.trim();
//		email_confirm.trim();
//		
//		System.out.println(email.getClass());
//		System.out.println(email_confirm.getClass());
//		System.out.println(email==email_confirm);
//		System.out.println(email.trim()==email_confirm.trim());
//		System.out.println(email==email);
//		System.out.println(email);
//		System.out.println(email_confirm);
//		
//		User user = userRepo.findByEmail(principal.getUsername());
//		if (email==email_confirm) 
//		{
//			user.setEmail(email);
//			System.out.println("new email is: " + user.getEmail());
//			return "accountManagement/email_confirmation";
//		}
//		else
//		{
//			System.out.println("Emails mismatch");
//			System.out.println(user.getEmail());
//			return "accountManagement/account";
//		}
		
	}
	
	// Edit password - recieves password values from form via POST request and @RequestParam
	@RequestMapping(value="/account/changePassword", method=RequestMethod.POST)
	public String editPassword(
			@AuthenticationPrincipal CustomUserDetails principal,
			@RequestParam("old_password") String old_password,
			@RequestParam("password") String password,
			@RequestParam("confirm_password") String confirm_password,			
			Model model)
	{
		
		//pull user from db and get a password encoder object
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = userRepo.findByEmail(principal.getUsername());
		
		//Access user password in database
		String dbPassword = user.getPassword();
		
		if (passwordEncoder.matches(old_password, dbPassword)) {
		    // Encode new password and store it
			user.setPassword(passwordEncoder.encode(password));
			userRepo.save(user);
			
			// return confirmation page	
			return "accountManagement/change_password";
			
		} else {
		    // Report error on error page
			return "accountManagement/password_error";
		}

		
//		Conditional to test if new passwords identical not working. No clue why
//		
//		if (password.trim() == confirm_password.trim())
//		{
//		
//			//pull user from db and get a password encoder object
//			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//			User user = userRepo.findByEmail(principal.getUsername());
//			
//			//Access user password in database
//			String dbPassword = user.getPassword();
//			
//			if (passwordEncoder.matches(old_password, dbPassword)) {
//			    // Encode new password and store it
//				System.out.println(password + " " + confirm_password);
//				user.setPassword(passwordEncoder.encode(password));
//				userRepo.save(user);
//				
//				// return confirmation page	
//				return "accountManagement/change_password";
//				
//			} else {
//			    // Report error on error page
//				return "accountManagement/password_error";
//			}
//		}
//		else 
//		{
//			System.out.println("password mismatch...");
//			System.out.println(password);
//			System.out.println(confirm_password);
//			return "accountManagement/password_error";
//		}
		
	}

}
