package com.chainsys.bookMyShow.model;

public class Theater {
	int theaterid;
	int cityid;
	String cityname;
	String theatername;
	int adminid;
	
	public int getAdminid() {
		return adminid;
	}

	public void setAdminid(int adminid) {
		this.adminid = adminid;
	}

	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	

	public int getCityid() {
		return cityid;
	}
	public void setCityid(int cityid) {
		this.cityid = cityid;
	}
	public int getTheaterid() {
		return theaterid;
	}
	public void setTheaterid(int theaterid) {
		this.theaterid = theaterid;
	}
	public String getTheatername() {
		return theatername;
	}
	public void setTheatername(String theatername) {
		this.theatername = theatername;
	}
	
	@Override
	public String toString() {
		return "Theater [theaterid=" + theaterid + ", cityid=" + cityid + ", cityname=" + cityname + ", theatername="
				+ theatername + ", adminid=" + adminid + ", getAdminid()=" + getAdminid() + ", getCityname()="
				+ getCityname() + ", getCityid()=" + getCityid() + ", getTheaterid()=" + getTheaterid()
				+ ", getTheatername()=" + getTheatername() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
