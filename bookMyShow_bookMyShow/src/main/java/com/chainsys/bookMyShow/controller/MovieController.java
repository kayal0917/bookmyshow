package com.chainsys.bookMyShow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chainsys.bookMyShow.business.MovieService;
import com.chainsys.bookMyShow.model.Allocation;
import com.chainsys.bookMyShow.model.Movie;

import jakarta.servlet.http.HttpSession;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;
    private static final String MOVIE_ATTRIBUTE_NAME = "movie";

    @GetMapping("/movie/byId")
    public String getMovieById(@RequestParam("imdbId") String imdbId, Model model) {
        Movie movie = movieService.getMovieDetailsById(imdbId);
        model.addAttribute(MOVIE_ATTRIBUTE_NAME, movie);
        return "movieDetails";
    }

    @GetMapping("/movie/byTitle")
    public String getMovieByTitle(@RequestParam("title") String title,HttpSession session, Model model) {
        Movie movie = movieService.getMovieDetailsByTitle(title);
        session.setAttribute("title", title);
        Allocation allocation =(Allocation) session.getAttribute("allocation");
        String location = allocation.getLocation();
        model.addAttribute("isMovieAvaiable",movieService.hasMovieAvailable(location, title));
        model.addAttribute(MOVIE_ATTRIBUTE_NAME, movie);
        return "movieDetail";
    }


    @GetMapping("/movie/search")
    public String searchMovies(@RequestParam("title") String title, Model model) {
        Movie movie = movieService.searchMoviesByTitle(title);
        if (movie != null) {
            model.addAttribute(MOVIE_ATTRIBUTE_NAME, movie);
            model.addAttribute("showBookNow", true);
        } else {
            model.addAttribute("showBookNow", false);
        }
        return "movieDetails"; 
    }
    @GetMapping("/search")
    public String searchMovies() {
        return "search";
    }

}
