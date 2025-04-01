package com.ticketbookingsystem.service;

import com.ticketbookingsystem.entity.Booking;
import com.ticketbookingsystem.entity.Showtime;
import com.ticketbookingsystem.exception.ConflictException;
import com.ticketbookingsystem.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    private Booking booking;

    @BeforeEach
    void setUp() {
        Showtime showtime = new Showtime();
        showtime.setId(1L);

        booking = new Booking();
        booking.setId(1L);
        booking.setShowtime(showtime);
        booking.setSeatNumber(15);
        booking.setUserId("John Doe");
    }

    @Test
    void bookTicket_shouldSaveBookingIfSeatIsAvailable() {
        when(bookingRepository.existsByShowtimeIdAndSeatNumber(1L, 15)).thenReturn(false);
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking result = bookingService.bookTicket(booking);
        assertEquals(booking, result);
    }

    @Test
    void bookTicket_shouldThrowIfSeatTaken() {
        when(bookingRepository.existsByShowtimeIdAndSeatNumber(1L, 15)).thenReturn(true);
        assertThrows(ConflictException.class, () -> bookingService.bookTicket(booking));
    }

    @Test
    void cancelBooking_shouldDeleteById() {
        bookingService.cancelBooking(1L);
        verify(bookingRepository, times(1)).deleteById(1L);
    }
}
