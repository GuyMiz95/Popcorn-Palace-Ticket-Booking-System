package com.ticketbookingsystem.service;

import com.ticketbookingsystem.entity.Movie;
import com.ticketbookingsystem.entity.Showtime;
import com.ticketbookingsystem.exception.ConflictException;
import com.ticketbookingsystem.exception.ResourceNotFoundException;
import com.ticketbookingsystem.repository.MovieRepository;
import com.ticketbookingsystem.repository.ShowtimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ShowtimeServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ShowtimeRepository showtimeRepository;

    @InjectMocks
    private ShowtimeService showtimeService;

    private Movie testMovie;
    private Showtime showtime;

    @BeforeEach
    void setUp() {
        testMovie = new Movie();
        testMovie.setId(1L);

        showtime = new Showtime();
        showtime.setMovie(testMovie);
        showtime.setTheater("Sample Theater");
        showtime.setStartTime(LocalDateTime.now().plusDays(1));
        showtime.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
        showtime.setPrice(50.0);
    }

    @Test
    void testAddConflictingShowtime_shouldThrowException() {
        when(showtimeRepository.findConflictingShowtimes(
                showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime()))
                .thenReturn(List.of(new Showtime()));

        assertThrows(ConflictException.class, () -> showtimeService.addShowtime(showtime));
    }

    @Test
    void testCreateShowtime_shouldSaveWhenNoConflict() {
        when(showtimeRepository.findConflictingShowtimes(
                showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime()))
                .thenReturn(List.of()); // no conflicts

        when(showtimeRepository.save(showtime)).thenReturn(showtime); // mock saving

        Showtime saved = showtimeService.addShowtime(showtime);

        assertEquals(showtime, saved); // assert it returns the saved showtime
    }

    @Test
    void getShowtimeById_shouldReturnShowtime() {
        showtime.setId(1L);
        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));

        Showtime result = showtimeService.getShowtimeById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Sample Theater", result.getTheater());
    }

    @Test
    void getShowtimeById_shouldThrowIfNotFound() {
        when(showtimeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> showtimeService.getShowtimeById(99L));
    }

    @Test
    void updateShowtime_shouldUpdateIfNoConflict() {
        showtime.setId(1L);
        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));
        when(showtimeRepository.findConflictingShowtimes(showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime()))
                .thenReturn(List.of()); // no conflict
        when(showtimeRepository.save(any(Showtime.class))).thenAnswer(inv -> inv.getArgument(0));

        Showtime result = showtimeService.updateShowtime(1L, showtime);
        assertEquals(showtime.getTheater(), result.getTheater());
    }

    @Test
    void updateShowtime_shouldThrowOnConflict() {
        showtime.setId(1L);
        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));
        when(showtimeRepository.findConflictingShowtimes(showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime()))
                .thenReturn(List.of(new Showtime())); // simulate conflict

        assertThrows(ConflictException.class, () -> showtimeService.updateShowtime(1L, showtime));
    }

    @Test
    void deleteShowtime_shouldDeleteById() {
        showtimeService.deleteShowtime(1L);
        verify(showtimeRepository, times(1)).deleteById(1L);
    }
}
