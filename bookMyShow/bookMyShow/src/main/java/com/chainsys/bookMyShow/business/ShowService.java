package com.chainsys.bookMyShow.business;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.chainsys.bookMyShow.dao.UserDAO;
import com.chainsys.bookMyShow.model.MovieDetails;

public class ShowService {
	 @Autowired
	    private UserDAO userDAO;
		public Map<String, List<MovieDetails>> getShowsInTheater(String movieName, String location) {
	        List<MovieDetails> list = userDAO.getShowDetails(location);
	        return list.stream()
	                   .filter(p -> p.getTitle().equals(movieName))
	                   .collect(Collectors.groupingBy(MovieDetails::getTheaterName));
	    }
	}


