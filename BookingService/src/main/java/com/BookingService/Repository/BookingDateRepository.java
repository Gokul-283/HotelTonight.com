package com.BookingService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingService.Entity.BookingDate;

public interface BookingDateRepository extends JpaRepository<BookingDate, Long> {

}
