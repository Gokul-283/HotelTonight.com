package com.BookingService.dto;

import jakarta.persistence.Column;

public class Rooms {
public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public double getBaseprice() {
		return baseprice;
	}
	public void setBaseprice(double baseprice) {
		this.baseprice = baseprice;
	}
private long id;
	
	private String roomType;
	private double baseprice;

}
