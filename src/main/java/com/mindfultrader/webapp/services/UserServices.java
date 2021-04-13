package com.mindfultrader.webapp.services;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mindfultrader.webapp.models.ConfirmationToken;
import com.mindfultrader.webapp.models.User;
import com.mindfultrader.webapp.repositories.ConfirmationTokenRepository;
import com.mindfultrader.webapp.repositories.UserRepository;

@Service
public class UserServices {
 
    @Autowired
    private UserRepository userRepo;
     
    @Autowired
    private PasswordEncoder passwordEncoder;
     
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepo;
 
     
    public void register(User user, String siteURL) 
    	throws UnsupportedEncodingException, MessagingException {
    	    String encodedPassword = passwordEncoder.encode(user.getPassword());
    	    user.setPassword(encodedPassword);
    	     
    	    //String randomCode = RandomString.make(64);
    	    //user.setVerificationCode(randomCode);
    	    user.setEnabled(false);
    	     
    	    userRepo.save(user);
    	    
    	    ConfirmationToken confirmationToken = new ConfirmationToken(user);
    	    confirmationTokenRepo.save(confirmationToken);
    	     
    	    sendVerificationEmail(user, siteURL, confirmationToken);
    	}
    
     
    private void sendVerificationEmail(User user, String siteURL, ConfirmationToken token) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "mindfultraderproject@gmail.com";
        String senderName = "MindfulTrader";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";
         
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
         
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
         
        content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
        
        
        

        /*if(token != null)
        {
            User user = userRepo.findByEmail(token.getUser().getEmail());
            user.setEnabled(true);
            userRepo.save(user);
            modelAndView.setViewName("accountVerified");
        }*/
        
        String verifyURL = siteURL + "/verify?code=" + token.getConfirmationToken();
         
        content = content.replace("[[URL]]", verifyURL);
         
        helper.setText(content, true);
         
        mailSender.send(message);
         
    }
    
    public boolean verify(String confirmationToken) {
    	
        ConfirmationToken token = confirmationTokenRepo.findByConfirmationToken(confirmationToken);
        
        if(token != null)
        {
            User user = userRepo.findByEmail(token.getUser().getEmail());
            if (user == null || user.isEnabled()) {
                return false;
            } else {
                token.setConfirmationToken(null);
                user.setEnabled(true);
                userRepo.save(user);
                 
                return true;
            }
            //user.setEnabled(true);
            //userRepo.save(user);
        }
        
        else {
        	return false;
        }
         
        
         
    }
         
}
