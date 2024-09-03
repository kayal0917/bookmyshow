package com.chainsys.bookMyShow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chainsys.bookMyShow.utill.EmailUtil;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmailController {

    @Autowired
    EmailUtil emailUtil;
    
    @GetMapping("/sendEmail")
    public String sendEmail(HttpSession session, @RequestParam("email") String toEmail){
        
        String subject = "🎉 Your Movie Tickets are Confirmed!";
        String body = "Dear user,\n\n" 
                + "Thanks for booking with us!.\n\n"
                + "\r\n"
                + "Here's an example of a movie ticket booking confirmation email template:\r\n"
                + "\r\n"
                + "Subject: 🎉 Your Movie Tickets are Confirmed! | [Cinema Name]\r\n"
                + "\r\n"
                + "Dear [Customer Name],\r\n"
                + "\r\n"
                + "Thank you for booking with [Cinema Name]! We're excited to have you at our theater. Here are your booking details:\r\n"
                + "\r\n";
        String confirmationMessage = "🎥 Movie: " + session.getAttribute("movie") + "\r\n"
                + "👤 User: " + session.getAttribute("username") + "\n"
                + "📅 Date: " + session.getAttribute("date") + "\r\n"
                + "⏰ Time: " + session.getAttribute("showList") + "\r\n"
                + "📍 Location: " + session.getAttribute("location") + "\r\n"
                + "💺 Seats: " + session.getAttribute("seats") + "\r\n"
                + "🔢 Seat Count: " + session.getAttribute("count")+ "\r\n"
                + "💵 Total Amount: " + session.getAttribute("totalAmount") + "\n"
                + "\r\n"
                + "We look forward to seeing you at the movies!🍿\r\n"
                + "\r\n"
                + "\n\n" + "Best regards,\n"
                + "TicketTrick Team";

        body += confirmationMessage;
                emailUtil.sendVerificationEmail(toEmail, subject, body);

        return "signup";
    }
}
