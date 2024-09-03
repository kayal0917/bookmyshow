package com.chainsys.bookMyShow.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chainsys.bookMyShow.model.Admin;
import com.chainsys.bookMyShow.model.Allocation;
import com.chainsys.bookMyShow.model.MovieDetails;
import com.chainsys.bookMyShow.model.ShowTime;
import com.chainsys.bookMyShow.model.Theater;
import com.chainsys.bookMyShow.model.Users;

@Repository
public interface UserDAO {
	public void registerAdmin(String userName, String hashedPassword, String location, String theaterName);
    public void insertUser(Users users) ;
	public List<Users> getAllUsers();
	public void removeUser(Users users);
	List<Users> search(String userName);
	public List<Theater> getAllTheater() ;
	public void removeTheater(Theater theater) ;
	public Object adminpassword(String username);
	public Object adminusername(String username);
	public String getuserLocation(String username);
	List<MovieDetails> getAllMovie();
	public void insertShowtime(int movieId, int theaterId, String showDate, String showTime) ;
	public Integer findMovieIdByTitle(String title) ;
	public void insertShow(Integer movieId, Integer theaterId, String showDate, String showTime) ;
	public void deleteShow(int showtimeId) ;
	public List<ShowTime> fetchShowList(int theaterId) ;
	public int findTheaterIdByName(String username);
	public Integer fetchMovieIdByTitle(String movieName);
	public List<MovieDetails> getShowDetails(String location) ;
	public List<MovieDetails> getShowDetails(String location,String movieName) ;
	public void insertBooking(int userId, int seatCount, String bookingDate, int totalAmount);
	public void insertMovie(MovieDetails movie) ;
	public List<Allocation> seatUser() ;

	public void seat( int userId, String seats, String showtime, int theaterId, String showDate, int movieId) ;
	public List<MovieDetails> viewImage(String title);
	public String getEmail(String username);
	public List<String> getAllCities();
	public boolean hasMovieAvailable(String location, String movieTitle);
	public void updateUser(String userName, String password);
	public void removeMovie(MovieDetails movie);
	public List<Admin> getAlladmin();
	public int getuserId(String userName);
	public int getmovieId(String movieName);
	public void addCity(String cityName);
	public void deleteCity(String cityName);
	String getTheaterName(int theaterId);
	String getusesrName(int userId);
    
	}
