package com.chainsys.bookMyShow.model;

public class Users {
	@Override
	public String toString() {
		return "Users [userName=" + userName + ", email=" + email + ", password=" + password + "]";
	}
	String userName;
	String email;
	String password;
	String location;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getuserName() {
		return userName;
	}
	public void setuserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
}
