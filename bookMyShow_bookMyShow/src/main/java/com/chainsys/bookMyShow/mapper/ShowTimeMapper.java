package com.chainsys.bookMyShow.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.bookMyShow.model.ShowTime;

public class ShowTimeMapper implements RowMapper<ShowTime>  {
	Integer movieId;
	Integer theaterId;
	String showDate;
	String showTime;
	@Override
	public ShowTime mapRow(ResultSet rs, int rowNum) throws SQLException {
		ShowTime showtime=new ShowTime();
        int movieId=rs.getInt(1);
        int theaterid=rs.getInt(1);
        String showDate=rs.getString(2);
        String showTime=rs.getString(3);

        showtime.setMovieId(movieId);
        showtime.setTheaterId(theaterid);
        showtime.setShowDate(showDate);
        showtime.setShowTime(showTime);

        return showtime;
	}
}
