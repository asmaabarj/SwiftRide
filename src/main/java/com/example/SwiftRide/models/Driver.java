package com.example.SwiftRide.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.example.SwiftRide.models.enums.AvailabilityStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity(name = "Drivers")
@DiscriminatorValue("Driver")
public class Driver extends User{
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AvailabilityStatus status;

    @Column(name = "availability_start", nullable = false)
    private LocalTime availabilityStart;

    @Column(name = "availability_end", nullable = false)
    private LocalTime availabilityEnd;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
}

