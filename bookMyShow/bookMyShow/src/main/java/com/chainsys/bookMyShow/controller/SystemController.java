package com.chainsys.bookMyShow.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.chainsys.bookMyShow.dao.UserDAO;
import com.chainsys.bookMyShow.model.Admin;
import com.chainsys.bookMyShow.model.Allocation;
import com.chainsys.bookMyShow.model.MovieDetails;
import com.chainsys.bookMyShow.model.ShowTime;
import com.chainsys.bookMyShow.model.Theater;
import com.chainsys.bookMyShow.model.Users;
import com.chainsys.bookMyShow.validation.validation;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@MultipartConfig
public class SystemController {

	@Autowired
	UserDAO userDAO;
	private static final String THEATER_ID_ATTRIBUTE_NAME = "theaterId";
	 private static final String SHOW_LIST_ATTRIBUTE_NAME = "showList";
	 private static final String ADMINDASHBOARD = "adminDashboard"; 
	 private static final String ALLOCATION = "allocation"; 
	 private static final String INDEX = "index";
	 private static final String ERROR = "error";

	@RequestMapping("/signup")
	public String signup(HttpSession session, Model model) {
		List<String> uniqueCitiesList = userDAO.getAllCities();
        session.setAttribute("cities", uniqueCitiesList);
      

		return "signUp";
	}
	@PostMapping("/signinn")
	public String signin(@RequestParam("username") String userName, 
	                     @RequestParam("password") String password,
	                     Model model, HttpSession session) {
		
	    if (userName.equals("joe09admin") && password.equals("Joe#09")) {
	        return "List";  
	    }
	    else if (password.equals(userDAO.adminpassword(userName))) {
	        int theaterId = userDAO.findTheaterIdByName(userName);
	        session.setAttribute(THEATER_ID_ATTRIBUTE_NAME, theaterId);
	        String theaterName=userDAO.getTheaterName(theaterId);
	        List<ShowTime> showList = userDAO.fetchShowList(theaterId);
	        model.addAttribute("theaterName",theaterName);
	        model.addAttribute(SHOW_LIST_ATTRIBUTE_NAME, showList);
	        return ADMINDASHBOARD;
	    } 	    
	    else {
	        String location = userDAO.getuserLocation(userName);
	        int userId=userDAO.getuserId(userName);
	        session.setAttribute("userId", userId);	      
	        Allocation allocation = new Allocation();
	        allocation.setLocation(location);
	        allocation.setUserId(userId);
	        allocation.setUserName(userName);
	        session.setAttribute("location", location);
	        session.setAttribute("username", userName);
	        session.setAttribute(ALLOCATION, allocation);
	        Set<String> seenTitles = ConcurrentHashMap.newKeySet();
	        List<MovieDetails> list = userDAO.getShowDetails(location).stream()
	                .filter(movie -> seenTitles.add(movie.getTitle()))
	                .collect(Collectors.toList());
	        String getEmail = userDAO.getEmail(userName);
	        session.setAttribute("email", getEmail);
	        model.addAttribute(SHOW_LIST_ATTRIBUTE_NAME, list);
	        List<String> uniqueCitiesList = userDAO.getAllCities();
	        session.setAttribute("cities", uniqueCitiesList);

	        return INDEX; 
	    }
	}


	@PostMapping("/changeLocation")
	public String changeLocation(@RequestParam("location") String location,HttpSession session,Model model) {
		Allocation allocation =(Allocation) session.getAttribute(ALLOCATION);
		if(allocation!=null) {
			allocation.setLocation(location);
			Set<String> seenTitles = ConcurrentHashMap.newKeySet();
		    List<MovieDetails> list = userDAO.getShowDetails(location).stream()
		            .filter(movie -> seenTitles.add(movie.getTitle())).collect(Collectors.toList());
		    model.addAttribute(SHOW_LIST_ATTRIBUTE_NAME, list);
		    return INDEX;
		}
		return ERROR;
	}
	

	@GetMapping("/showList")
	public String showList() {
		return SHOW_LIST_ATTRIBUTE_NAME;
	}
	private static final String MOVIE = "movie";
	@PostMapping("/movie")
	public String movie()
	{
		
		return MOVIE;
	}
	
		
	@PostMapping("/ticket")
	public String ticket(HttpSession session ,Model model ) 
	{
	    Allocation allocation = (Allocation) session.getAttribute(ALLOCATION);
	    int movieId=userDAO.getmovieId(allocation.getMovieName());
	    userDAO.insertBooking(allocation.getUserId(), allocation.getSeatCount(), allocation.getBookingDate(), allocation.getTotalAmount());
	    model.addAttribute(ALLOCATION, allocation);
	    int count=allocation.getSeatCount();
	    session.setAttribute("count", count);
	    String[] seats = allocation.getSeat().split(",");
	    for (String seat : seats) {
	        userDAO.seat(allocation.getUserId(), seat, allocation.getShowTime(), allocation.getTheaterId(), allocation.getBookingDate(),movieId);
	    }

	    return "ticket";
	}


	@GetMapping("/index")
	public String indexs() {
		return INDEX;
	}
	
	private static final String SIGNIN = "signin";
	@RequestMapping("/signUp")
	public String signUpp(@RequestParam("userName") String userName, @RequestParam("email") String email,
	        @RequestParam("password") String password, @RequestParam("location") String location, Model model,
	        HttpSession session) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String encryptedPassword = bcrypt.encode(password);
	    
	    if (!validation.isValidName(userName)) {
	        model.addAttribute(ERROR, "Invalid name format");
	        return "errorPage.jsp";
	    }

	    if (!validation.isValidEmail(email)) {
	        model.addAttribute(ERROR, "Invalid email format");
	        return "errorPage.jsp";
	    }

	    Users users = new Users();
	    users.setuserName(userName);
	    users.setEmail(email);
	    users.setPassword(encryptedPassword);
	    users.setLocation(location);

	    userDAO.insertUser(users);

	    session.setAttribute("location", location);
	    session.setAttribute("email", email);
	    

	    return SIGNIN;
	}

	private static final String LIST = "userList";
	private static final String USER = "users";
	@GetMapping("/userList")
	public String getAllUser(Model model) {
		List<Users> users = userDAO.getAllUsers();
		model.addAttribute(USER, users);
		return LIST;
	}
	@GetMapping("/cityLists")
	public String getAllCities(Model model) 
	{
		List<String> uniqueCitiesList = userDAO.getAllCities();
		model.addAttribute("uniqueCitiesList", uniqueCitiesList);
			
		return "cityList";
	}
	@PostMapping("/addcity")
	public String addCity(String cityName) {
		userDAO.addCity(cityName);
		return "redirect:/cityLists";
	}
	   @PostMapping("/deletecity")
	    public String deleteCity(@RequestParam("cityName") String cityName) {
	        userDAO.deleteCity(cityName);
	        return "redirect:/cityLists";
	    }
	@GetMapping("/adminLists")
	public String getAllAdmin(Model model) {
		List<Admin> admin = userDAO.getAlladmin();
		model.addAttribute("user", admin);
		return "adminList";
	}
	@GetMapping("/deleteuser")
	public String removeUser(@RequestParam("userName") String userName, Model model) {
		Users users = new Users();
		users.setuserName(userName);
		userDAO.removeUser(users);

		List<Users> users1 = userDAO.getAllUsers();
		model.addAttribute(USER, users1);
		return LIST;

	}
	@PostMapping("/updateuser")
    public String updateUser(@RequestParam("userName") String userName,
	        @RequestParam("password")String password,
	        HttpSession session, Model model) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String encryptedPassword = bcrypt.encode(password);
		userDAO.updateUser(userName,encryptedPassword);
		
		List<Users> users1 = userDAO.getAllUsers();
		model.addAttribute(USER, users1);
		return LIST;
	
    }

	@GetMapping("/theateradmin")
	public String registerUser(@RequestParam("userName") String userName, @RequestParam("password") String password,
			@RequestParam("location") String location, @RequestParam("theater") String theaterName,
			HttpSession session) {

		userDAO.registerAdmin(userName, password, location, theaterName);
		return SIGNIN;
	}

	@RequestMapping("/adminHome")
	public String adminHome() {
		return "adminHome";
	}

	@RequestMapping("/List")
	public String list() {
		return "list";
	}

	@RequestMapping("/adminsignup")
	public String adminSigUp() {
		return "adminSignUp";
	}

	@RequestMapping("/adminDasboard")
	public String adminDasboard() {
		return "adminDasboard";
	}



	@GetMapping("/theaterList")
	public String getAllTheater(Model model) {
		List<Theater> theater1 = userDAO.getAllTheater();
		model.addAttribute(USER, theater1);
		return "theaterList";
	}

	@GetMapping("/deleteTheater")
	public String removeTheater(@RequestParam("theatername") String theatername, Model model) {
		Theater theater = new Theater();
		theater.setTheatername(theatername);
		userDAO.removeTheater(theater);
		List<Theater> theater1 = userDAO.getAllTheater();
		model.addAttribute(USER, theater1);
		return "theaterList";
	}
	
	@PostMapping("/deleteMovie")
	public String removeMovie(@RequestParam("title") String title, Model model) {
		MovieDetails movie = new MovieDetails();
		movie.setTitle(title);
		userDAO.removeMovie(movie);	
		
		List<MovieDetails> movie1 = userDAO.getAllMovie();
		for(MovieDetails object:movie1)
        {
            byte[] document = object.getImageUrl();
            String getDocument = Base64.getEncoder().encodeToString(document);
            object.setBase64(getDocument);
        }
		model.addAttribute("allmovies", movie1);
		return "movieList";
	}

	@PostMapping("/movielist")
	public String movieLists(@RequestParam("title") String title,
			@RequestParam("genre") String genre, @RequestParam("imageUrl") MultipartFile imageUrl) throws IOException {
		MovieDetails movie = new MovieDetails();
		if (!imageUrl.isEmpty()) {			
			byte[] image = imageUrl.getBytes();
			movie.setTitle(title);
			movie.setGenre(genre);
			movie.setImageUrl(image);
			userDAO.insertMovie(movie);
		} else {
			return MOVIE;
		}

		return MOVIE;
	}

	@RequestMapping("/AllMovie")
	public String allMovies(Model model) {
		List<MovieDetails> movie1 = userDAO.getAllMovie();
		for(MovieDetails object:movie1)
        {
            byte[] document = object.getImageUrl();
            String getDocument = Base64.getEncoder().encodeToString(document);
            object.setBase64(getDocument);
        }
		model.addAttribute("allmovies", movie1);
		
		return "movieList";
	}

	@GetMapping("/addShow")
	public String addshows() {
		return "addshow";
	}
	@PostMapping("/view")
	  public String view(Model model, @RequestParam("title") String title)
    {
        List<MovieDetails> getImage = userDAO.viewImage(title);
        for(MovieDetails property:getImage)
        {
            byte[] getImage1 = property.getImageUrl();
            String toBase = Base64.getEncoder().encodeToString(getImage1);
            property.setBase64(toBase);
        }
        model.addAttribute("allmovies", getImage);
        return "ImageView";
    }

	@PostMapping("/payment")
	public String payment() {
		return "payment";
	}

	@PostMapping("/paymentprocess")
	public String paymentprocess() 
	{
		return "paymentprocess";
	}

	@PostMapping("/addShow")
	public String addShow(@RequestParam("MovieName") String movieName, @RequestParam("showDate") String showDate,
			@RequestParam("ShowTime") String showTime, HttpSession session, Model model) {
		Integer movieId = userDAO.fetchMovieIdByTitle(movieName);
		int theaterId = (int) session.getAttribute(THEATER_ID_ATTRIBUTE_NAME);
		if (movieId == null) {
			model.addAttribute("message", "Movie not found");
			return ADMINDASHBOARD;
		}
		userDAO.insertShow(movieId, theaterId, showDate, showTime);

		model.addAttribute("message", "Show added successfully");
		return ADMINDASHBOARD;
	}

	@PostMapping("/deleteShow")
	public String deleteShow(@RequestParam("showtime_id") int showtimeId, HttpServletRequest request,Model model,HttpSession session) {
		userDAO.deleteShow(showtimeId);
		int theaterId= (int) session.getAttribute(THEATER_ID_ATTRIBUTE_NAME);
		List<ShowTime> showList = userDAO.fetchShowList(theaterId);
        model.addAttribute(SHOW_LIST_ATTRIBUTE_NAME, showList);
		return ADMINDASHBOARD;
	}

	@PostMapping("/showList")
	public String showList(HttpSession session, Model model) {
		int theaterId = (int) session.getAttribute(THEATER_ID_ATTRIBUTE_NAME);
		List<ShowTime> showList = userDAO.fetchShowList(theaterId);
		for (ShowTime theater : showList) {
			for (ShowTime showTime : showList) {
				
			}
		}
	
		model.addAttribute(SHOW_LIST_ATTRIBUTE_NAME, showList);
		return SHOW_LIST_ATTRIBUTE_NAME;
	}

	@PostMapping("/processBooking")
	public String processBooking(@RequestParam("selectedSeats") String seats,
	                             @RequestParam("selectedSeatsCount") int seatCount,
	                             HttpSession session) {
	    Allocation allocation = (Allocation) session.getAttribute(ALLOCATION);
	    allocation.setSeat(seats);
	    session.setAttribute("seats", seats);
	    allocation.setSeatCount(seatCount);
	    session.setAttribute("seatCount", seats);


	    String[] seatArray = seats.split(",");
	    int totalAmount = 0;

	    for (String seat : seatArray) {
	        char row = seat.charAt(0);
	        if (row >= 'A' && row <= 'B') {
	            totalAmount += 150; // Front seats price
	        } else if (row >= 'C' && row <= 'F') {
	            totalAmount += 200; // Middle seats price
	        } else {
	            totalAmount += 100; // Back seats price
	        }
	    }

	    allocation.setTotalAmount(totalAmount);
	    session.setAttribute("totalAmount", totalAmount);

	    return "payment";
	}


	@GetMapping("/control" )
	public String index(HttpSession session, Model model) {
		model.addAttribute("greetings", "Welcome");
		return SIGNIN;
	}

	@RequestMapping("/adminSignIn")
	public String adminSignIns(Model model) {
		return "adminSignin";
	}

	@RequestMapping("/front")
	public String front() {
		return "front";
	}

}
