package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
