package com.ticketbookingsystem.controller;

import com.ticketbookingsystem.entity.Showtime;
import com.ticketbookingsystem.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing showtimes.
 */
@RestController
@RequestMapping("/api/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    /**
     * Adds a new showtime.
     */
    @PostMapping
    public ResponseEntity<Showtime> addShowtime(@RequestBody Showtime showtime) {
        return new ResponseEntity<>(showtimeService.addShowtime(showtime), HttpStatus.CREATED);
    }

    /**
     * Updates an existing showtime by its ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Showtime> updateShowtime(@PathVariable Long id, @RequestBody Showtime showtime) {
        return ResponseEntity.ok(showtimeService.updateShowtime(id, showtime));
    }

    /**
     * Retrieves a showtime by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getShowtimeById(@PathVariable Long id) {
        return ResponseEntity.ok(showtimeService.getShowtimeById(id));
    }

    /**
     * Deletes a showtime by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return ResponseEntity.noContent().build();
    }


}
