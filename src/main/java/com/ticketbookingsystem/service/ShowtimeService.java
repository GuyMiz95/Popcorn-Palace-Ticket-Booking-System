package com.ticketbookingsystem.service;

import com.ticketbookingsystem.entity.Showtime;
import com.ticketbookingsystem.exception.ConflictException;
import com.ticketbookingsystem.exception.ResourceNotFoundException;
import com.ticketbookingsystem.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing showtime logic, including conflict handling.
 */
@Service
@RequiredArgsConstructor
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    /**
     * Adds a new showtime after checking for time conflicts in the same theater.
     *
     * @param showtime the showtime to add
     * @return the saved showtime
     * @throws ConflictException if there's a scheduling conflict
     */
    public Showtime addShowtime(Showtime showtime) {
        if (hasConflict(showtime)) {
            throw new ConflictException("Conflicting showtime exists for this theater.");
        }
        return showtimeRepository.save(showtime);
    }

    /**
     * Updates an existing showtime with new details, checking for conflicts.
     *
     * @param id ID of the showtime to update
     * @param updatedShowtime the new showtime details
     * @return the updated showtime
     */
    public Showtime updateShowtime(Long id, Showtime updatedShowtime) {
        Showtime showtime = showtimeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Showtime not found"));

        showtime.setMovie(updatedShowtime.getMovie());
        showtime.setTheater(updatedShowtime.getTheater());
        showtime.setStartTime(updatedShowtime.getStartTime());
        showtime.setEndTime(updatedShowtime.getEndTime());
        showtime.setPrice(updatedShowtime.getPrice());

        if (hasConflict(showtime)) {
            throw new ConflictException("Conflicting showtime exists after update.");
        }
        return showtimeRepository.save(showtime);
    }

    /**
     * Retrieves a showtime by its ID.
     *
     * @param id ID of the showtime
     * @return the found showtime
     */
    public Showtime getShowtimeById(Long id) {
    return showtimeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Showtime not found"));
    }

    /**
     * Deletes a showtime by ID.
     *
     * @param id the ID of the showtime to delete
     */
    public void deleteShowtime(Long id) {
        showtimeRepository.deleteById(id);
    }



    private boolean hasConflict(Showtime showtime) {
        List<Showtime> conflicts = showtimeRepository.findConflictingShowtimes(showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime());
        return !conflicts.isEmpty();
    }
}
