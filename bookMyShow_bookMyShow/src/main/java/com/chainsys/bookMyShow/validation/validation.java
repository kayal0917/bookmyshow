package com.chainsys.bookMyShow.validation;

public class validation {
	  public static boolean isValidName(String userName) {
		     
	        return userName != null && !userName.isEmpty() && userName.matches("[a-zA-Z\\s]+");
	    }

	    public static boolean isValidEmail(String email) {
	      
	        return email != null && !email.isEmpty() && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
	    }

	    public static boolean isValidPassword(String password) {
	        return password != null && !password.isEmpty() && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
	    }
  
}
