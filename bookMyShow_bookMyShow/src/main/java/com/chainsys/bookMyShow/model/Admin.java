package com.chainsys.bookMyShow.model;

public class Admin {
	String userName;
	String password;

	@Override
	public String toString() {
		return "Admin [userName=" + userName + ", password=" + password + "]";
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
