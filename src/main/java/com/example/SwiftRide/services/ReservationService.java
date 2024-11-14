package com.example.SwiftRide.services;

import com.example.SwiftRide.dto.ReservationDTO;
import com.example.SwiftRide.models.Reservation;
import com.example.SwiftRide.models.enums.ReservationStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ReservationService {


        List<ReservationDTO> getAllReservations();

        Optional<ReservationDTO> getReservationById(Long id);

        ReservationDTO createReservation(ReservationDTO reservation);

        ReservationDTO updateReservation(Long id, ReservationDTO reservationDetails);

        void deleteReservation(Long id);
}
