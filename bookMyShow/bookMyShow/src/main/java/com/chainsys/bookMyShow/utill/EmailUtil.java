package com.chainsys.bookMyShow.utill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

@Repository
public class EmailUtil {
	
		@Autowired
	    private JavaMailSender mailSender;

	    public void sendVerificationEmail(String toEmail, String subject, String body) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(toEmail);
	        message.setSubject(subject);
	        message.setText(body);
	        message.setFrom("jerjay354@gmail.com");

	        mailSender.send(message);
	    }




}

