package com.tajiang.leifeng.model;

public class Taker {
	
	private String userId;

    private String userName;

    private int sex;

    private String apartmentid;

    private String phone;

    private Byte state;
    
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public String getApartmentid() {
		return apartmentid;
	}

	public void setApartmentid(String apartmentid) {
		this.apartmentid = apartmentid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

}