package com.chainsys.bookMyShow.model;

import java.util.List;

public class Shows {
	
	private String theaterName;
    private List<ShowTime> showTime;
    
	public String getTheaterName() {
		return theaterName;
	}
	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}
	public List<ShowTime> getShowTime() {
		return showTime;
	}
	public void setShowTime(List<ShowTime> showTime) {
		this.showTime = showTime;
	}
	@Override
	public String toString() {
		return "Shows [theaterName=" + theaterName + ", showTime=" + showTime + "]";
	}  
}
