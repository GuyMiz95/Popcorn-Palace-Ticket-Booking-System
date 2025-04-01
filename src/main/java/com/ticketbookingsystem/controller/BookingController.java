package com.ticketbookingsystem.controller;

import com.ticketbookingsystem.entity.Booking;
import com.ticketbookingsystem.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing bookings.
 */
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     * Books a ticket.
     */
    @PostMapping
    public ResponseEntity<Booking> bookTicket(@RequestBody Booking booking) {
        return new ResponseEntity<>(bookingService.bookTicket(booking), HttpStatus.CREATED);
    }

    /**
     * Cancels a booking by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }
}
