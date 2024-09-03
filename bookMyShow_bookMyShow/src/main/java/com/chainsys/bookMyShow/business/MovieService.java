package com.chainsys.bookMyShow.business;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chainsys.bookMyShow.dao.UserDAO;
import com.chainsys.bookMyShow.model.Movie;





@Service
public class MovieService {
	
	@Autowired
	private UserDAO userDao;

    @Value("${omdb.api.key}")
    private String apiKey;
    private static final String baseUrl = "http://www.omdbapi.com/";


    public Movie getMovieDetailsById(String imdbId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?i=%s&apikey=%s", baseUrl, imdbId, apiKey);
        return restTemplate.getForObject(url, Movie.class);
    }

    public Movie getMovieDetailsByTitle(String title) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?t=%s&apikey=%s", baseUrl, title, apiKey);
        return restTemplate.getForObject(url, Movie.class);
    }

    public Movie getMovieDetailsByTitleAndYear(String title, String year) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?t=%s&y=%s&apikey=%s", baseUrl, title, year, apiKey);
        return restTemplate.getForObject(url, Movie.class);
    }

    public Movie searchMoviesByTitle(String title) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?s=%s&apikey=%s", baseUrl, title, apiKey);
        return restTemplate.getForObject(url, Movie.class);
    }
    private static final String BASE_URL2 = "https://api.themoviedbss.org/3";
    private static final String API_KEY = "fae55f73667d820050fd9369e276852d";
    
    
    public List<Map<String, Object>> getMoviesByYear(String year) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s/discover/movie?primary_release_year=%s&api_key=%s", BASE_URL2, year, API_KEY);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        return (List<Map<String, Object>>) response.get("results");
    }


    public boolean hasMovieAvailable(String location, String movieTitle) {
        return userDao.hasMovieAvailable(location, movieTitle);
    }
}
