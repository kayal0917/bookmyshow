package com.chainsys.bookMyShow.model;

public class Allocation {
	int bookingId;
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	int userId;
	String userName;
	int SeatCount;
	String bookingDate;
	int totalAmount;
	int seatId;
	String seat;
	String showTime;
	int theaterId;
	int movieId;
	String ShowDate;
	String movieName;
	String location;
    String title;
	public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getSeatCount() {
		return SeatCount;
	}

	public void setSeatCount(int seatCount) {
		SeatCount = seatCount;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public int getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(int theaterId) {
		this.theaterId = theaterId;
	}

	public String getShowDate() {
		return ShowDate;
	}

	public void setShowDate(String showDate) {
		ShowDate = showDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	@Override
	public String toString() {
		return "Allocation [bookingId=" + bookingId + ", userName=" + userName + ", SeatCount=" + SeatCount
				+ ", bookingDate=" + bookingDate + ", totalAmount=" + totalAmount + ", seatId=" + seatId + ", seat="
				+ seat + ", showTime=" + showTime + ", theaterId=" + theaterId + ", ShowDate=" + ShowDate
				+ ", movieName=" + movieName + ", location=" + location + "]";
	}
	
}
