package com.example.SwiftRide.services;

import com.example.SwiftRide.dto.ReservationAnalyticsDTO;
import com.example.SwiftRide.dto.ReservationDTO.ReservationRequestDTO;
import com.example.SwiftRide.dto.ReservationDTO.ReservationResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO);

    ReservationResponseDTO getReservationById(Long id);

    List<ReservationResponseDTO> getAllReservations();

    ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO reservationRequestDTO);

    void deleteReservation(Long id);

    boolean isDriverAvailable(long driverId, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime);

    boolean isVehicleAvailable(long vehicleId, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime);

    boolean isReservationStatusValid(long reservationId);

    ReservationAnalyticsDTO getAnalytics();
}
