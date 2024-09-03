package com.chainsys.bookMyShow.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.bookMyShow.model.Theater;

public class TheaterMapper implements RowMapper<Theater> {

	@Override
	public Theater mapRow(ResultSet rs, int rowNum) throws SQLException {
		Theater theater=new Theater();
        int Theaterid=rs.getInt(1);
        String cityname=rs.getString(2);

        String Theatername=rs.getString(3);

        theater.setTheaterid(Theaterid);
        theater.setCityname(cityname);

        theater.setTheatername(Theatername);

        return theater;
	}

}
