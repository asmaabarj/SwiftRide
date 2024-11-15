package com.example.SwiftRide.services.impl;

import com.example.SwiftRide.dto.ReservationDTO.ReservationRequestDTO;
import com.example.SwiftRide.dto.ReservationDTO.ReservationResponseDTO;
import com.example.SwiftRide.exceptions.DistanceTooLongException;
import com.example.SwiftRide.exceptions.DriverNotAvailableException;
import com.example.SwiftRide.exceptions.ReservationStatusException;
import com.example.SwiftRide.exceptions.VehicleNotAvailableException;
import com.example.SwiftRide.models.Driver;
import com.example.SwiftRide.models.Reservation;
import com.example.SwiftRide.models.enums.ReservationStatus;
import com.example.SwiftRide.repositories.ReservationRepository;
import com.example.SwiftRide.services.VehicleService;
import com.example.SwiftRide.utils.ReservationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createReservation_ThrowsDistanceTooLongException() {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setDistanceKm(150.0);
        request.setStartTime(LocalDateTime.now());
        request.setEndTime(LocalDateTime.now().plusHours(2));

        DistanceTooLongException exception = assertThrows(DistanceTooLongException.class,
                () -> reservationService.createReservation(request));
        assertEquals("Maximum distance is 100km", exception.getMessage());
    }

    @Test
    void createReservation_ThrowsDriverNotAvailableException() {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setDriverId(1L);
        request.setVehicleId(1L);
        request.setStartTime(LocalDateTime.now());
        request.setEndTime(LocalDateTime.now().plusHours(2));
        request.setDistanceKm(50.0);

        Reservation existingReservation = new Reservation();
        existingReservation.setDriver(new Driver());
        existingReservation.setStartTime(LocalDateTime.now().minusHours(1));
        existingReservation.setEndTime(LocalDateTime.now().plusHours(3));

        when(reservationRepository.findByDriverIdAndTimeOverlap(
                eq(1L), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(existingReservation));

        DriverNotAvailableException exception = assertThrows(DriverNotAvailableException.class,
                () -> reservationService.createReservation(request));
        assertEquals("Driver is not available for the selected time.", exception.getMessage());
    }

    @Test
    void createReservation_ThrowsVehicleNotAvailableException() {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setDriverId(1L);
        request.setVehicleId(1L);
        request.setStartTime(LocalDateTime.now());
        request.setEndTime(LocalDateTime.now().plusHours(2));
        request.setDistanceKm(50.0);

        when(reservationRepository.findByVehicleIdAndTimeOverlap(anyLong(), any(), any()))
                .thenReturn(Arrays.asList(new Reservation())); // Vehicle is not available

        VehicleNotAvailableException exception = assertThrows(VehicleNotAvailableException.class,
                () -> reservationService.createReservation(request));
        assertEquals("Vehicle is not available for the selected time.", exception.getMessage());
    }


    @Test
    void updateReservation_ThrowsReservationStatusException() {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setDistanceKm(50.0);

        Reservation existingReservation = new Reservation();
        existingReservation.setStatus(ReservationStatus.CONFIRMED); // Invalid status for update

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(existingReservation));

        ReservationStatusException exception = assertThrows(ReservationStatusException.class,
                () -> reservationService.updateReservation(1L, request));
        assertEquals("Cannot update a reservation with a status of CONFIRMED or COMPLETED.", exception.getMessage());
    }

    @Test
    void deleteReservation_ThrowsEntityNotFoundException() {
        when(reservationRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> reservationService.deleteReservation(1L));
        assertEquals("Reservation not found with ID: 1", exception.getMessage());
    }

    @Test
    void deleteReservation_SuccessfulDeletion() {
        when(reservationRepository.existsById(1L)).thenReturn(true);

        reservationService.deleteReservation(1L);

        verify(reservationRepository, times(1)).deleteById(1L);
    }


}
