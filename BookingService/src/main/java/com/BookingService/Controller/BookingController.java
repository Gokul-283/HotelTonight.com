package com.BookingService.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.BookingService.client.PropertyClient;
import com.BookingService.dto.ApiResponse;
import com.BookingService.dto.BookingDto;
import com.BookingService.dto.PropertyDto;
import com.BookingService.dto.RoomAvailability;
import com.BookingService.dto.Rooms;
import com.BookingService.Entity.BookingDate;
import com.BookingService.Entity.Bookings;
import com.BookingService.Repository.BookingDateRepository;
import com.BookingService.Repository.BookingRepository;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

	 Optional<RoomAvailability> matchedRoom =java.util.Optional.empty();
	@Autowired
	private PropertyClient propertyClient;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private BookingDateRepository bookingDateRepository;

	
	@PostMapping("/add-to-cart")
	public ApiResponse<List<String>> cart(@RequestBody BookingDto bookingDto) {
		
		
		ApiResponse<List<String>> apiResponse = new ApiResponse<>();
		
		List<String> messages = new ArrayList<>();
		
		ApiResponse<PropertyDto> response = propertyClient.getPropertyById(bookingDto.getPropertyId());

		ApiResponse<Rooms> roomType = propertyClient.getRoomType(bookingDto.getRoomId());
		
		ApiResponse<List<RoomAvailability>> totalRoomsAvailable = propertyClient.getTotalRoomsAvailable(bookingDto.getRoomId());
		
		List<RoomAvailability> availableRooms = totalRoomsAvailable.getData();
		
		//Logic to check available rooms based on date and count
		for(LocalDate date: bookingDto.getDate()) {
			boolean isAvailable = availableRooms.stream()
			        .anyMatch(ra -> ra.getAvailableDate().equals(date) && ra.getAvailableCount()>0);
			
			    
			    System.out.println("Date " + date + " available: " + isAvailable);
			    
			    if (!isAvailable) {
			    	 messages.add("Room not available on: " + date);
			    	 apiResponse.setMessage("Sold Out");
			 		 apiResponse.setStatuscode(500);
			 		 apiResponse.setData(messages);
			 		 return apiResponse;
			    }
			    matchedRoom = availableRooms.stream().filter(ra->ra.getAvailableDate().equals(date) && ra.getAvailableCount()>0).findFirst();
			    
		}
		//Save it to Booking Table with status pending
		Bookings bookings = new Bookings();
		bookings.setName(bookingDto.getName());
		bookings.setEmail(bookingDto.getEmail());
		bookings.setMobile(bookingDto.getMobile());
		bookings.setPropertyName(response.getData().getName());
		bookings.setStatus("pending");
		bookings.setTotalPrice(roomType.getData().getBaseprice()*bookingDto.getTotalNigths());
		Bookings savedBooking = bookingRepository.save(bookings);
		
		for(LocalDate date: bookingDto.getDate()) {
			BookingDate  bookingDate = new BookingDate();
			bookingDate.setDate(date);
			bookingDate.setBookings(savedBooking);
			BookingDate savedBookingDate = bookingDateRepository.save(bookingDate);
			if(savedBookingDate!=null) {
				propertyClient.updateRoomCount(matchedRoom.get().getId(), date);
			}
		}
		
		
		
		
		return null;
	}

}