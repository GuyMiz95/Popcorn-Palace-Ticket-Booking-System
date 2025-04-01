package com.ticketbookingsystem.service;

import com.ticketbookingsystem.entity.Movie;
import com.ticketbookingsystem.exception.ResourceNotFoundException;
import com.ticketbookingsystem.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setGenre("Sci-Fi");
        movie.setDuration(148);
        movie.setRating(8.8);
        movie.setReleaseYear(2010);
    }

    @Test
    void createMovie_shouldReturnSavedMovie() {
        when(movieRepository.save(movie)).thenReturn(movie);
        Movie result = movieService.addMovie(movie);
        assertEquals(movie, result);
    }

    @Test
    void updateMovieByTitle_shouldUpdateAndReturnMovie() {
        when(movieRepository.findByTitle("Inception")).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenAnswer(inv -> inv.getArgument(0));
        Movie updatedMovie = new Movie(null, "Inception", "Sci-Fi", 150, 9.0, 2010);

        Movie result = movieService.updateMovie("Inception", updatedMovie);

        assertEquals(updatedMovie.getDuration(), result.getDuration());
        assertEquals(updatedMovie.getRating(), result.getRating());
    }

    @Test
    void updateMovieByTitle_shouldThrowIfNotFound() {
        when(movieRepository.findByTitle("Unknown")).thenReturn(Optional.empty());
        Movie updated = new Movie();
        assertThrows(ResourceNotFoundException.class, () -> movieService.updateMovie("Unknown", updated));
    }

    @Test
    void deleteMovieByTitle_shouldDeleteIfExists() {
        when(movieRepository.findByTitle("Inception")).thenReturn(Optional.of(movie));
        movieService.deleteMovie("Inception");
        verify(movieRepository, times(1)).delete(movie);
    }

    @Test
    void getAllMovies_shouldReturnList() {
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.getAllMovies();
        assertEquals(1, result.size());
    }

    @Test
    void updateMovieById_shouldUpdateAndReturnMovie() {
        Movie updatedMovie = new Movie(null, "Tenet", "Action", 150, 7.5, 2020);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Movie result = movieService.updateMovieById(1L, updatedMovie);

        assertEquals("Tenet", result.getTitle());
        assertEquals(150, result.getDuration());
        assertEquals(2020, result.getReleaseYear());
    }

    @Test
    void updateMovieById_shouldThrowIfNotFound() {
        when(movieRepository.findById(999L)).thenReturn(Optional.empty());

        Movie updated = new Movie();
        assertThrows(ResourceNotFoundException.class, () -> movieService.updateMovieById(999L, updated));
    }

    @Test
    void deleteMovieById_shouldDeleteIfExists() {
        movieService.deleteMovieById(1L);
        verify(movieRepository, times(1)).deleteById(1L);
    }
}
