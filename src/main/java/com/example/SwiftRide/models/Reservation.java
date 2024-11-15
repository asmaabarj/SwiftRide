package com.example.SwiftRide.models;

import com.example.SwiftRide.models.enums.ReservationStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Embedded
    @AttributeOverride(name = "city", column = @Column(name = "departure_city"))
    @AttributeOverride(name = "district", column = @Column(name = "departure_district"))
    private Address departureAddress;

    @Embedded
    @AttributeOverride(name = "city", column = @Column(name = "arrival_city"))
    @AttributeOverride(name = "district", column = @Column(name = "arrival_district"))
    private Address arrivalAddress;

    @Column(nullable = false)
    private Double price;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Column(name = "distance_km", nullable = false)
    private Double distanceKm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
}
