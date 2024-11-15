package com.example.SwiftRide.models;

import javax.persistence.*;
import java.time.LocalTime;

import com.example.SwiftRide.models.enums.AvailabilityStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
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

    @Column(name = "availabilityStart", nullable = false)
    private LocalTime availabilityStart;

    @Column(name = "availabilityEnd", nullable = false)
    private LocalTime availabilityEnd;
}

