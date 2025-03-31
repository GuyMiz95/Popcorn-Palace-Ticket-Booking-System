package com.ticketbookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    private String theater;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double price;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL)
    private List<Booking> bookings;

}
