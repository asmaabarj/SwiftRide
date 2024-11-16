package com.example.SwiftRide.dto.ReservationDTO;

import com.example.SwiftRide.dto.AddressDTO;
import com.example.SwiftRide.models.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequestDTO {

    @NotNull(message = "Departure address is required.")
    private AddressDTO departureAddress;

    @NotNull(message = "Arrival address is required.")
    private AddressDTO arrivalAddress;

    @NotNull(message = "Distance in kilometers is required.")
    @DecimalMin(value = "0.1", message = "Distance must be at least 0.1 km.")
    private Double distanceKm;

    @NotNull(message = "Start time is required.")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required.")
    private LocalDateTime endTime;

    @NotNull(message = "Reservation status is required.")
    private ReservationStatus status;

    @NotNull(message = "Driver ID is required.")
    @Positive(message = "Driver ID must be a positive number.")
    private Long driverId;

    @NotNull(message = "Client ID is required.")
    @Positive(message = "Client ID must be a positive number.")
    private Long clientId;

    @NotNull(message = "Vehicle ID is required.")
    @Positive(message = "Vehicle ID must be a positive number.")
    private Long vehicleId;
}
