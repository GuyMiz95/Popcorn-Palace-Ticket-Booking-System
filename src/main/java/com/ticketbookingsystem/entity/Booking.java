package com.ticketbookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"showtime_id", "seatNumber"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Getter
    @Setter
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    private String seatNumber;
    private String customerInfo;

}
