package com.example.SwiftRide.dto;

import com.example.SwiftRide.models.enums.ReservationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDTO {
    private Long id;
    private LocalDateTime reservationDateTime;
    private AddressDTO departureAddress;
    private AddressDTO arrivalAddress;
    private Double price;
    private Double distanceKm;
    private ReservationStatus status;
    private Long driverId;
    private Long vehicleId;
}
