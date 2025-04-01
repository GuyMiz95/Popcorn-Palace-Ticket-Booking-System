package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for handling Booking entities and seat availability checks.
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByShowtimeIdAndSeatNumber(Long id, Integer seat_number);
}
