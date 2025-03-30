package com.ticketbookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Getter
    @Setter
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String genre;
    private Integer duration; // in minutes
    private Double rating;
    private Integer release_year;

}
