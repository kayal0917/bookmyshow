package com.chainsys.bookMyShow.mapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.bookMyShow.model.MovieDetails;

public class ImageMapper implements RowMapper<MovieDetails>{
	    @Override
	    public MovieDetails mapRow(ResultSet rs, int rowNum) throws SQLException {	        
	    	MovieDetails property = new MovieDetails();	        
	        Blob image = rs.getBlob("image_url");
	        if (image != null) 
	        {
	            int blobLength = (int) image.length();
	            byte[] blobAsBytes = image.getBytes(1, blobLength);
	            property.setImageUrl(blobAsBytes);
	        }	        
	        return property;
	    }
}
