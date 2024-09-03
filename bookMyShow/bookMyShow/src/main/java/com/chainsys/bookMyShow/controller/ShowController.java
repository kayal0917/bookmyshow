package com.chainsys.bookMyShow.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chainsys.bookMyShow.dao.UserDAO;
import com.chainsys.bookMyShow.model.Allocation;
import com.chainsys.bookMyShow.model.MovieDetails;

import jakarta.servlet.http.HttpSession;

@Controller
public class ShowController {
	
	@Autowired
	UserDAO userDAO;
	private static final String ALLOCATION_ATTRIBUTE_NAME = "allocation";
	
	@GetMapping("/showTime")
	public String showsInTheater(@RequestParam("movieName") String movieName,@RequestParam("showDate") String showDate,HttpSession session) {
		Allocation allocation =(Allocation) session.getAttribute(ALLOCATION_ATTRIBUTE_NAME);
		allocation.setBookingDate(showDate);
		allocation.setMovieName(movieName);
		session.setAttribute("date", showDate);
		session.setAttribute("movie", movieName);
		List<MovieDetails> list = userDAO.getShowDetails(allocation.getLocation());
		Map<String, List<MovieDetails>> showTime = list.stream().filter(p->p.getTitle().equals(movieName)).collect(Collectors.groupingBy(MovieDetails::getTheaterName));
		session.setAttribute(ALLOCATION_ATTRIBUTE_NAME, allocation);
		session.setAttribute("showList", showTime);
		
		return "shows";
	}	
	@GetMapping("/seat")
    public String seat(@RequestParam("selectedShowTime")String showTime,@RequestParam("selectedShowDate")String showDate,@RequestParam("theaterId") int theaterId,Model model,HttpSession session) {
		Allocation allocation =(Allocation) session.getAttribute(ALLOCATION_ATTRIBUTE_NAME);
		List<Allocation> seatUsers = userDAO.seatUser();
				allocation.setBookingDate(showDate);
		allocation.setShowTime(showTime);
		session.setAttribute("showList", showTime);
		allocation.setTheaterId(theaterId);
		session.setAttribute(ALLOCATION_ATTRIBUTE_NAME, allocation);
		char[] rows = {'A', 'B', 'C', 'D', 'E', 'F'};
		boolean[] isSeatFree = new boolean[rows.length * 20]; // Assuming 20 seats per row
		int seatIndex = 0;

		for (char row : rows) {
		    for (int i = 1; i <= 20; i++) {
		        String seat = "" + row + i;
		        boolean seatAllocated = false;

		        for (Allocation seatUser : seatUsers) {
		            if (seatUser.getShowDate().equals(showDate) && 
		                seatUser.getShowTime().equals(showTime) && 
		                seatUser.getSeat().equals(seat)) {
		                seatAllocated = true;
		                break;
		            }
		        }

		        isSeatFree[seatIndex] = !seatAllocated;
		        seatIndex++;
		    }
		}
		
	model.addAttribute("rows", rows);
		model.addAttribute("isSeatFree",isSeatFree);
        return "seat"; 
    }
}
