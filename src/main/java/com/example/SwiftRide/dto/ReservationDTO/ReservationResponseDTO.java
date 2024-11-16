package com.example.SwiftRide.dto.ReservationDTO;

import com.example.SwiftRide.dto.AddressDTO;
import com.example.SwiftRide.dto.ClientDTO;
import com.example.SwiftRide.dto.DriverDTO;
import com.example.SwiftRide.dto.VehicleDTO;
import com.example.SwiftRide.models.Driver;
import com.example.SwiftRide.models.enums.ReservationStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationResponseDTO {
    private Long Id;
    private AddressDTO departureAddress;
    private AddressDTO arrivalAddress;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double price;
    private Double distanceKm;
    private ReservationStatus status;
    private DriverDTO driverDTO;
    private ClientDTO clientDTO;
    private VehicleDTO vehicleDTO;
}
