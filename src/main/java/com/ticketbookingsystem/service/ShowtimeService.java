package com.ticketbookingsystem.service;

import com.ticketbookingsystem.entity.Showtime;
import com.ticketbookingsystem.exception.ConflictException;
import com.ticketbookingsystem.exception.ResourceNotFoundException;
import com.ticketbookingsystem.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    public Showtime createShowtime(Showtime showtime) {
        if (hasConflict(showtime)) {
            throw new RuntimeException("Conflicting showtime exists for this theater.");
        }
        return showtimeRepository.save(showtime);
    }

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

    public void deleteShowtime(Long id) {
        showtimeRepository.deleteById(id);
    }

    public Showtime getShowtimeById(Long id) {
        return showtimeRepository.findById(id).orElseThrow(() -> new RuntimeException("Showtime not found"));
    }

    private boolean hasConflict(Showtime showtime) {
        List<Showtime> conflicts = showtimeRepository.findConflictingShowtimes(showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime());
        return !conflicts.isEmpty();
    }
}
