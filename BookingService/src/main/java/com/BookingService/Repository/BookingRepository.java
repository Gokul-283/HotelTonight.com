package com.BookingService.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingService.Entity.Bookings;

public interface BookingRepository extends JpaRepository<Bookings, Long> {

}