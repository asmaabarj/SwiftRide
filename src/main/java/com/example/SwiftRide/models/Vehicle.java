package com.example.SwiftRide.models;

import javax.persistence.*;

import com.example.SwiftRide.models.enums.AvailabilityStatus;
import com.example.SwiftRide.models.enums.VehiculeType;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "mileage", nullable = false)
    private double mileage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AvailabilityStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private VehiculeType type;
}
