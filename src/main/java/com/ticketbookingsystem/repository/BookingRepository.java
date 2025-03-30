package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByShowtimeIdAndSeatNumber(Long id, String seat_number);
}
