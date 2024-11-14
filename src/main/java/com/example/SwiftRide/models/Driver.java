package com.example.SwiftRide.models;

import javax.persistence.*;
import java.time.LocalTime;

import com.example.SwiftRide.models.enums.AvailabilityStatus;
import lombok.*;

@Entity
@Table(name = "drivers")
@Data
@NoArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AvailabilityStatus status;

    @Column(name = "availability_start", nullable = false)
    private LocalTime availabilityStart;

    @Column(name = "availability_end", nullable = false)
    private LocalTime availabilityEnd;
}

