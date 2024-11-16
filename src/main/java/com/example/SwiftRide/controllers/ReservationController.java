package com.example.SwiftRide.controllers;

import com.example.SwiftRide.dto.ReservationAnalyticsDTO;
import com.example.SwiftRide.dto.ReservationDTO.ReservationRequestDTO;
import com.example.SwiftRide.dto.ReservationDTO.ReservationResponseDTO;
import com.example.SwiftRide.services.ReservationService;
import com.example.SwiftRide.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@Validated
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@Valid @RequestBody ReservationRequestDTO reservationRequest) {
        ReservationResponseDTO reservationResponse = reservationService.createReservation(reservationRequest);
        return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        List<ReservationResponseDTO> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable Long id) {
        ReservationResponseDTO reservationResponse = reservationService.getReservationById(id);
        if (reservationResponse == null) {
            throw new ResourceNotFoundException("Reservation not found with ID: " + id);
        }
        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> updateReservation(@PathVariable Long id,
                                                                    @Valid @RequestBody ReservationRequestDTO reservationRequest) {
        ReservationResponseDTO updatedReservation = reservationService.updateReservation(id, reservationRequest);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/analytics")
    public ReservationAnalyticsDTO getAnalytics() {
        return reservationService.getAnalytics();
    }
}
