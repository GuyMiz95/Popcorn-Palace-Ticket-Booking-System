package com.ticketbookingsystem.service;

import com.ticketbookingsystem.entity.Booking;
import com.ticketbookingsystem.exception.ConflictException;
import com.ticketbookingsystem.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service layer for handling movie ticket bookings.
 */
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    /**
     * Books a ticket for a specific showtime and seat if not already taken.
     *
     * @param booking the booking request
     * @return the confirmed booking
     * @throws ConflictException if the seat is already booked
     */
    public Booking bookTicket(Booking booking) {
        boolean seatTaken = bookingRepository.existsByShowtimeIdAndSeatNumber(booking.getShowtime().getId(), booking.getSeatNumber());

        if (seatTaken) {
            throw new ConflictException("Seat already booked for this showtime.");
        }

        return bookingRepository.save(booking);
    }

    /**
     * Cancels a booking by its ID.
     *
     * @param id the ID of the booking to cancel
     */
    public void cancelBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
