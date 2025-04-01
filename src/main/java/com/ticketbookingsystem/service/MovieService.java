package com.ticketbookingsystem.service;


import com.ticketbookingsystem.entity.Movie;
import com.ticketbookingsystem.exception.ResourceNotFoundException;
import com.ticketbookingsystem.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for handling business logic related to movies.
 */
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    /**
     * Saves a new movie to the database.
     *
     * @param movie the movie to be added
     * @return the saved movie
     */
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    /**
     * Updates an existing movie by its ID.
     *
     * @param id ID of the movie to update
     * @param updatedMovie the new movie details
     * @return the updated movie
     */
    public Movie updateMovieById(Long id, Movie updatedMovie) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        movie.setTitle(updatedMovie.getTitle());
        movie.setGenre(updatedMovie.getGenre());
        movie.setDuration(updatedMovie.getDuration());
        movie.setRating(updatedMovie.getRating());
        movie.setReleaseYear(updatedMovie.getReleaseYear());

        return movieRepository.save(movie);
    }

    /**
     * Updates an existing movie using its title.
     *
     * @param title the title of the movie to update
     * @param updatedMovie the new movie details
     * @return the updated movie
     */
    public Movie updateMovie(String title, Movie updatedMovie) {
        Movie movie = movieRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        movie.setGenre(updatedMovie.getGenre());
        movie.setDuration(updatedMovie.getDuration());
        movie.setRating(updatedMovie.getRating());
        movie.setReleaseYear(updatedMovie.getReleaseYear());

        return movieRepository.save(movie);
    }

    /**
     * Deletes a movie by its ID.
     *
     * @param id the ID of the movie to delete
     */
    public void deleteMovieById(Long id) {
        movieRepository.deleteById(id);
    }

    public void deleteMovie(String title) {
        Movie movie = movieRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        movieRepository.delete(movie);
    }

    /**
     * Retrieves all movies stored in the database.
     *
     * @return list of all movies
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}
