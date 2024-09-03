package com.chainsys.bookMyShow.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.bookMyShow.model.Users;

public class UserMapper  implements RowMapper<Users> {


    @Override
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
    	Users users=new Users();
        String userName=rs.getString("user_name");
        String email=rs.getString("email");
        String password=rs.getString("password");
        String location=rs.getString("location");


        users.setuserName(userName);
     users.setEmail(email);
     users.setPassword(password);  
     users.setLocation(location);


        return users;
    }
}
