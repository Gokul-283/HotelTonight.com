package com.BookingService.dto;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "room_availability")
public class RoomAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate availableDate;
    private int availableCount;
    private double price;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Rooms room;

	public long getId() {
		return id;
	}

	public LocalDate getAvailableDate() {
		return availableDate;
	}

	public int getAvailableCount() {
		return availableCount;
	}

	public double getPrice() {
		return price;
	}

	public Rooms getRoom() {
		return room;
	}

	public void setId(long id) {
		this.id = id;
	}

    
   	
	

}
