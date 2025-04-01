package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing and managing Movie entities.
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);
}
