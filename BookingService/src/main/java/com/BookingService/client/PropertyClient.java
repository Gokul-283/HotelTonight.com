package com.BookingService.client;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BookingService.dto.ApiResponse;
import com.BookingService.dto.PropertyDto;
import com.BookingService.dto.RoomAvailability;

@FeignClient(name = "PROPERTYSERVICE") 
public interface PropertyClient {
	
	@GetMapping("/api/v1/property/property-id")
	public ApiResponse<PropertyDto> getPropertyById(@RequestParam long id);
	
	@GetMapping("/api/v1/property/room-available-room-id")
	public ApiResponse<List<RoomAvailability>> getTotalRoomsAvailable(@RequestParam long id);


	@GetMapping("/api/v1/property/room-id")
	public ApiResponse<com.BookingService.dto.Rooms> getRoomType(@RequestParam long id);
@PutMapping("/updateRoomCount")
	public ApiResponse<Boolean> updateRoomCount(@RequestParam long id, @RequestParam LocalDate date);

}
