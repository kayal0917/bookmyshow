package com.chainsys.bookMyShow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chainsys.bookMyShow.dao.UserDAO;
import com.chainsys.bookMyShow.model.Allocation;

import jakarta.servlet.http.HttpSession;

public class PaymentController {
	
	@Autowired
	UserDAO userDAO;
	
	@PostMapping("/processBooking")
    public String processBooking(@RequestParam("selectedSeats") String seats,HttpSession session) {
    	Allocation allocation= (Allocation) session.getAttribute("allocation");
    	allocation.setSeat(seats);
    	session.setAttribute("seats", seats);
    	session.setAttribute("seatCount", seats.split(",").length);
    	session.setAttribute("amount", seats.split(",").length*200);
    	session.setAttribute("seat", seats.split(","));
        return "payment";
    }
}
