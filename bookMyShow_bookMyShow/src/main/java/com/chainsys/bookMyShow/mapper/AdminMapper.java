package com.chainsys.bookMyShow.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.bookMyShow.model.Admin;

public class AdminMapper implements RowMapper<Admin>  {
	 @Override
	    public Admin mapRow(ResultSet rs, int rowNum) throws SQLException 
	    {
		 Admin admin=new Admin();
	        String userName=rs.getString("admin_name");
	        String password=rs.getString("password");


	        admin.setUserName(userName);
	     admin.setPassword(password);  


	        return admin;
	    }
}
