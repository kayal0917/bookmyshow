package com.chainsys.bookMyShow.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chainsys.bookMyShow.dao.UserDAO;
import com.chainsys.bookMyShow.mapper.AdminMapper;
import com.chainsys.bookMyShow.mapper.FlimMapper;
import com.chainsys.bookMyShow.mapper.ImageMapper;
import com.chainsys.bookMyShow.mapper.SeatMapper;
import com.chainsys.bookMyShow.mapper.ShowDetailsMapper;
import com.chainsys.bookMyShow.mapper.TheaterMapper;
import com.chainsys.bookMyShow.mapper.UserMapper;
import com.chainsys.bookMyShow.model.Admin;
import com.chainsys.bookMyShow.model.Allocation;
import com.chainsys.bookMyShow.model.MovieDetails;
import com.chainsys.bookMyShow.model.ShowTime;
import com.chainsys.bookMyShow.model.Theater;
import com.chainsys.bookMyShow.model.Users;

import jakarta.servlet.annotation.MultipartConfig;

@Repository
@MultipartConfig
public class MovieImp implements UserDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	UserMapper mapper;
	AdminMapper admin;
	FlimMapper flim;
	TheaterMapper theatermapper;
	ImageMapper imageMapper;

	public String adminpassword(String username) {
		System.out.println(username);
		String adminPassword = "select password from admin where admin_name=? ";
		String password = null;
		try {
			password = jdbcTemplate.queryForObject(adminPassword, String.class, username);
		} catch (Exception e) {
		}
		return password;
	}

	public String getuserLocation(String username) {
		String query = "select location from users where user_name=? ";
//		String location = null;
//		try {
//			location = jdbcTemplate.queryForObject(query, String.class, username);
//		} catch (Exception e) {
//		}
//		System.out.println(username);
//		System.out.println(location);
		return jdbcTemplate.queryForObject(query, String.class, username);
	}
	public String adminusername(String username) {
		String adminusername = "select user_name from users where user_name=? and delete_user=0";
		String password = null;
		try {
			password = jdbcTemplate.queryForObject(adminusername, String.class, username);
		} catch (Exception e) {
		}
		return password;
	}

	@Override
	public void registerAdmin(String userName, String password, String location, String theaterName) {
		String register = "INSERT INTO admin (admin_name, password) VALUES (?, ?)";
	
		jdbcTemplate.update(register, userName, password);
		
		int adminid = getAdminId();
		int cityId = getCityId(location);
		
		String theater = "INSERT INTO theaters (theater_name, city_id,admin_id) VALUES (?,?,?)";
		jdbcTemplate.update(theater, theaterName, cityId, adminid);
	}

	public int getAdminId() {
		String getAdminId = "SELECT MAX(admin_id) AS last_admin_id FROM admin";
		Integer adminId = jdbcTemplate.queryForObject(getAdminId, Integer.class);
		return adminId != null ? adminId : 0;
	}

	private int getCityId(String location) {
		String city = "SELECT city_id FROM cities WHERE city_name = ?";
		Integer id= jdbcTemplate.queryForObject(city, Integer.class, location);
		return id;
	}
	public List<String> getAllCities() {
	    String allcities = "SELECT city_name FROM cities where delete_city=0";
	    return jdbcTemplate.query(allcities, (rs, rowNum) -> rs.getString("city_name"));
	}

	public void insertUser(Users users) {
		String insert = "insert into users(user_name, email,password,location) values (?,?,?,?)";
		Object[] params = { users.getuserName(), users.getEmail(), users.getPassword(), users.getLocation() };
		jdbcTemplate.update(insert, params);

	}

	@Override
	public List<Users> getAllUsers() {
		String select = "select user_name,email,password,location from users where delete_user=0";
		List<Users> userList = jdbcTemplate.query(select, new UserMapper());
		return userList;
	}

	public void removeUser(Users users) {
		String delete = "update users set delete_user=1 where user_name=?";
		Object[] userName = { users.getuserName() };
		jdbcTemplate.update(delete, userName);

	}

	@Override
	public List<Users> search(String userName) {
		String search = String.format("SELECT user_name,email,password FROM users "
				+ "WHERE (user_name LIKE '%%%s%%' OR email LIKE '%%%s%%'OR password LIKE '%%%s%%') "
				+ "AND delete_user=0", userName, userName, userName);
		return jdbcTemplate.query(search, new UserMapper());
	}

	public List<Theater> getAllTheater() {
		String select = "SELECT t.theater_id, c.city_name, t.theater_name\r\n" + "FROM theaters t\r\n"
				+ "INNER JOIN cities c ON t.city_id = c.city_id && t.delete_theater=0;";
		List<Theater> theaterList = jdbcTemplate.query(select, new TheaterMapper());
		return theaterList;
	}

	public void removeTheater(Theater theater) {
		String delete = "update theaters set delete_user=1 where theater_name=?";
		Object[] theatername = { theater.getTheatername() };
		jdbcTemplate.update(delete, theatername);

	}

	@Override
	public void insertMovie(MovieDetails movie) {
		String insertmovie = "INSERT INTO movie_details (title,  genre,  image_url) VALUES (?,?,?)";
		Object[] params = { movie.getTitle(),movie.getGenre(),movie.getImageUrl() };
		jdbcTemplate.update(insertmovie, params);
	}

	public Integer findMovieIdByTitle(String title) {
		String query = "SELECT movie_id FROM movie_details WHERE title = ?";
		return jdbcTemplate.queryForObject(query, new Object[] { title }, Integer.class);
	}

	public void insertShowtime(int movieId, int theaterId, String showDate, String showTime) {
		String insertShowtime = "INSERT INTO showtimes (movie_id, theater_id, show_date, show_time) VALUES (?,?,?,?)";
		jdbcTemplate.update(insertShowtime, movieId, theaterId, Date.valueOf(showDate), Time.valueOf(showTime));
	}

	@Override
	public List<MovieDetails> getAllMovie() {
		String select = "select title,genre,image_url from movie_details where delete_movie=0";
		List<MovieDetails> movieList = jdbcTemplate.query(select, new FlimMapper());
		return movieList;
	}
	
	@Override
	public List<Admin> getAlladmin() {
		String select = "select admin_name,password from admin where delete_admin=0";
		List<Admin> adminList = jdbcTemplate.query(select, new AdminMapper());
		return adminList;
	}

    
	public Integer fetchMovieIdByTitle(String movieName) {
		System.out.println(movieName);
		String fetchMovieIdSql = "SELECT movie_id FROM movie_details WHERE title = ?";
		return jdbcTemplate.queryForObject(fetchMovieIdSql,Integer.class, movieName );
	}

	public void insertShow(Integer movieId, Integer theaterId, String showDate, String showTime) {
		String insertShowSql = "INSERT INTO movie_show (movie_id, theater_id, show_date, show_time) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(insertShowSql, movieId, theaterId, showDate, showTime);
	}

	public void deleteShow(int showtimeId) {
		String deleteShowSql = "DELETE FROM movie_show WHERE showtime_id = ?";
		jdbcTemplate.update(deleteShowSql, showtimeId);
	}

	public int findTheaterIdByName(String username) {
			
		String query = "SELECT theater_id FROM theaters WHERE admin_id=(SELECT admin_id FROM admin WHERE admin_name=?);";
		return jdbcTemplate.queryForObject(query, Integer.class, new Object[] { username });
	}

	public List<ShowTime> fetchShowList(int theaterId) {
		String fetchShowListSql = "SELECT s.showtime_id, m.title AS movieName, s.theater_id, s.show_date, s.show_time "
				+ "FROM movie_show s " + "JOIN movie_details m ON s.movie_id = m.movie_id WHERE s.theater_id="
				+ theaterId;
		return jdbcTemplate.query(fetchShowListSql, (rs, rowNum) -> {
			ShowTime showTime = new ShowTime();
			showTime.setShowtimeId(rs.getInt("showtime_id"));
			showTime.setMovieName(rs.getString("movieName"));
			showTime.setTheaterId(rs.getInt("theater_id"));
			showTime.setShowDate(rs.getString("show_date"));
			showTime.setShowTime(rs.getString("show_time"));
			return showTime;
		});
	}	
	public List<MovieDetails> getShowDetails(String location) {
	    String select = "SELECT st.showtime_id, st.movie_id, md.title, md.genre, md.image_url, st.theater_id, t.city_id, t.theater_name, st.show_date, st.show_time, t.delete_theater AS theater_delete_user, t.admin_id FROM movie_show st INNER JOIN theaters t ON st.theater_id = t.theater_id INNER JOIN movie_details md ON st.movie_id = md.movie_id WHERE t.city_id = ? LIMIT 0, 1000;";
	    int cityId = getCityId(location);
	    @SuppressWarnings("deprecation")
	    List<MovieDetails> detailsList = jdbcTemplate.query(select, new Object[] { cityId }, new ShowDetailsMapper());
	    return detailsList;
	}
	public List<MovieDetails> getShowDetails(String location, String movieName) {
		String select = "SELECT " + "    st.showtime_id, " + "    st.movie_id, " + "    md.title, "
				 + "    md.genre, "
				 + "    md.image_url, " + "    st.theater_id, " + "    t.city_id, "
				+ "    t.theater_name, " + "    st.show_date, " + "    st.show_time, "
				+ "    t.delete_user AS theater_delete_user, " + "    t.admin_id " + "FROM " + "    show_table st "
				+ "INNER JOIN " + "    theaters t ON st.theater_id = t.theater_id " + "INNER JOIN "
				+ "    movie_details md ON st.movie_id = md.movie_id " + "WHERE "
				+ "    t.city_id = ? AND md.title = ?  " + "LIMIT 0, 1000;";

		int cityId = getCityId(location);
		@SuppressWarnings("deprecation")
		List<MovieDetails> detailsList = jdbcTemplate.query(select, new Object[] { cityId, movieName },
				new ShowDetailsMapper());
		return detailsList;
	}
	public boolean hasMovieAvailable(String location, String movieTitle) {
	    String select = "SELECT COUNT(*) FROM movie_show st " +
	                    "INNER JOIN theaters t ON st.theater_id = t.theater_id " +
	                    "INNER JOIN movie_details md ON st.movie_id = md.movie_id " +
	                    "WHERE t.city_id = ? AND md.title = ?";

	    int cityId = getCityId(location);
	    int count = jdbcTemplate.queryForObject(select, new Object[]{cityId, movieTitle}, Integer.class);

	    return count > 0;
	}

	@Override
	public void insertBooking(int userId, int seatCount, String bookingDate, int totalAmount) {
		String sql = "INSERT INTO bookings (user_id,seat_count, booking_date, total_amount) " + "VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, userId, seatCount, bookingDate, totalAmount);
	}

	@Override
	public List<Allocation> seatUser() {
		String select = "SELECT  user_id, seats, showtime, theater_id, show_date FROM seats";
		List<Allocation> bookingList = jdbcTemplate.query(select, new SeatMapper());
		return bookingList;
	}

	@Override
	public List<MovieDetails> viewImage(String title) 
    {
        String retrive = "select image_url from movie_details where title=?";
        List<MovieDetails> list = jdbcTemplate.query(retrive, new ImageMapper(), title);
        return list;
    }

	@Override
	public String getEmail(String username) {
		String email = "select email from users where user_name=? ";	
		return jdbcTemplate.queryForObject(email, String.class,username);
	}

	@Override
	public void seat(int userId, String seats, String showtime, int theaterId, String showDate, int movieId)
	{
		String sql = "INSERT INTO seats ( user_id, seats, showtime, theater_id, show_date,movie_id) VALUES ( ?, ?, ?, ?, ?, ?) ";

		jdbcTemplate.update(sql, userId, seats, showtime, theaterId, showDate,movieId);		
	}

	@Override
	public void updateUser(String userName, String password) {
		 String update = "update users set password=? where user_name=?";
	        Object[] params = { password, userName };
	        jdbcTemplate.update(update, params);		
	}

	@Override
	public void removeMovie(MovieDetails movie)
	{
		
		
		 String update = "update movie_details set delete_movie=0 where title=?";
		 Object[] theatername = { movie.getTitle() };
	        jdbcTemplate.update(update, theatername);
		
	}
	@Override
	public int getuserId(String userName) {
		String select="select user_id from users where user_name=?";
		return jdbcTemplate.queryForObject(select,Integer.class,userName);
	}
	@Override
	public int getmovieId(String movieName) {
		String select="select movie_id from movie_details where title=?";
		return jdbcTemplate.queryForObject(select,Integer.class,movieName);
	}

	@Override
	public void addCity(String cityName) {
		String insert="insert into cities (city_name)values(?)";
		jdbcTemplate.update(insert,cityName);
	}

	@Override
	public void deleteCity(String cityName) {
		String delete = "update cities set delete_city=1 where city_name=?";
		jdbcTemplate.update(delete, cityName);		
	}
	@Override
	public String getTheaterName(int theaterId) {
		String select="select theater_name from theaters where theater_id=?";
		return jdbcTemplate.queryForObject(select,String.class,theaterId);
	}
	@Override
	public String getusesrName(int userId) {
		String select="select user_name from users where user_id=?";
		return jdbcTemplate.queryForObject(select,String.class,userId);
	}
}
