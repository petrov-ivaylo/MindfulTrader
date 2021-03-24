package com.mindfultrader.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Controller for user account pages. 
 * 
 * User must be able to change password
 * User must be able to change their email
 * User must be able to delete their account
 * 
 */

@Controller
public class EditAccountController {
	
	
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
	
	// Edit email
	@RequestMapping("/account/editEmail")
	public String editEmail()
	{
		//CODE HERE
		return "accountManagement/change_email";
	}
	
	// Edit password
	@RequestMapping("/account/changePassword")
	public String editPassword()
	{
		//CODE HERE
		
		
		return "accountManagement/change_password";
	}

}
