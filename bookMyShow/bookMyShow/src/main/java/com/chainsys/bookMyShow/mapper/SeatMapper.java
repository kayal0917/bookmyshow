package com.chainsys.bookMyShow.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.bookMyShow.model.Allocation;

public class SeatMapper implements RowMapper<Allocation>  {


	
	    @Override
	    public Allocation mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Allocation seat = new Allocation();
	        seat.setUserId(rs.getInt("user_id"));
	        seat.setSeat(rs.getString("seats"));
	        seat.setShowTime(rs.getString("showtime"));
	        seat.setTheaterId(rs.getInt("theater_id"));
	        seat.setShowDate(rs.getString("show_date"));
	        return seat;
	    }
	}


