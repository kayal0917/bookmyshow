package com.chainsys.bookMyShow.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.bookMyShow.model.MovieDetails;

public class FlimMapper implements RowMapper<MovieDetails> {	
	    @Override
	    public MovieDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	MovieDetails movie = new MovieDetails();
	        movie.setTitle(rs.getString("title"));	        
	        movie.setGenre(rs.getString("genre"));	       
	        movie.setImageUrl(rs.getBytes("image_url"));
	        return movie;
	    }
	}