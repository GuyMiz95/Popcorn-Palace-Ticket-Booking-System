package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long>{
    @Query("SELECT s FROM Showtime s " +
            "WHERE s.theater = :theater " +
            "AND s.startTime <= :endTime " +
            "AND s.endTime >= :startTime")
    List<Showtime> findConflictingShowtimes(@Param("theater") String theater,
                                            @Param("startTime") LocalDateTime startTime,
                                            @Param("endTime") LocalDateTime endTime);
}
