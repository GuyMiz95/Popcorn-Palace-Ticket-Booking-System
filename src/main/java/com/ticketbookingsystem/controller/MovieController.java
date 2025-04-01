package com.ticketbookingsystem.controller;

import com.ticketbookingsystem.entity.Movie;
import com.ticketbookingsystem.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing movies.
 */
@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    /**
     * Adds a new movie.
     */
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        return new ResponseEntity<>(movieService.addMovie(movie), HttpStatus.CREATED);
    }

    /**
     * Deletes a movie by its title.
     */
    @DeleteMapping("/{movieTitle}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String movieTitle) {
        movieService.deleteMovie(movieTitle);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates an existing movie by its title.
     */
    @PostMapping("/update/{movieTitle}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String movieTitle, @RequestBody Movie updatedMovie) {
        return new ResponseEntity<>(movieService.updateMovie(movieTitle, updatedMovie), HttpStatus.OK);
    }

    /**
     * Retrieves all movies.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }
}
