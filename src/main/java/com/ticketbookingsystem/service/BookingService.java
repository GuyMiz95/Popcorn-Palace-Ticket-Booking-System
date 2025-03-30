package com.ticketbookingsystem.service;

import com.ticketbookingsystem.entity.Booking;
import com.ticketbookingsystem.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking bookTicket(Booking booking) {
        boolean seatTaken = bookingRepository.existsByShowtimeIdAndSeatNumber(booking.getShowtime().getId(), booking.getSeatNumber());

        if (seatTaken) {
            throw new RuntimeException("Seat already booked for this showtime.");
        }

        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long id) {
        bookingRepository.deleteById(id);
    }

}
