package com.example.SwiftRide.services.impl;

import com.example.SwiftRide.dto.ReservationAnalyticsDTO;
import com.example.SwiftRide.dto.ReservationDTO.ReservationRequestDTO;
import com.example.SwiftRide.dto.ReservationDTO.ReservationResponseDTO;
import com.example.SwiftRide.exceptions.DistanceTooLongException;
import com.example.SwiftRide.exceptions.DriverNotAvailableException;
import com.example.SwiftRide.exceptions.ReservationStatusException;
import com.example.SwiftRide.exceptions.VehicleNotAvailableException;
import com.example.SwiftRide.models.Address;
import com.example.SwiftRide.models.Reservation;
import com.example.SwiftRide.repositories.ReservationRepository;
import com.example.SwiftRide.services.ReservationService;
import com.example.SwiftRide.services.VehicleService;
import com.example.SwiftRide.utils.ReservationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO) {
        if (reservationRequestDTO.getDistanceKm() > 100) {
            throw new DistanceTooLongException("Maximum distance is 100km");
        }

        if (!isDriverAvailable(reservationRequestDTO.getDriverId(), reservationRequestDTO.getStartTime(), reservationRequestDTO.getEndTime())) {
            throw new DriverNotAvailableException("Driver is not available for the selected time.");
        }

        if (!isVehicleAvailable(reservationRequestDTO.getVehicleId(), reservationRequestDTO.getStartTime(), reservationRequestDTO.getEndTime())) {
            throw new VehicleNotAvailableException("Vehicle is not available for the selected time.");
        }

        Reservation reservation = reservationMapper.toReservation(reservationRequestDTO);

        Double price = reservation.getDistanceKm() * vehicleService.getVehicle(reservation.getVehicle().getId()).getType().getPricePerKm();
        reservation.setPrice(price);
        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.toResponseDTO(savedReservation);
    }

    @Override
    public ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO reservationRequestDTO) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with ID: " + id));

        if (!isReservationStatusValid(id)) {
            throw new ReservationStatusException("Cannot update a reservation with a status of CONFIRMED or COMPLETED.");
        }

        if (reservationRequestDTO.getDistanceKm() > 100) {
            throw new DistanceTooLongException("Maximum distance is 100km");
        }

        if (!isDriverAvailable(reservationRequestDTO.getDriverId(), reservationRequestDTO.getStartTime(), reservationRequestDTO.getEndTime())) {
            throw new DriverNotAvailableException("Driver is not available for the selected time.");
        }

        if (!isVehicleAvailable(reservationRequestDTO.getVehicleId(), reservationRequestDTO.getStartTime(), reservationRequestDTO.getEndTime())) {
            throw new VehicleNotAvailableException("Vehicle is not available for the selected time.");
        }

        reservationMapper.updateEntityFromDTO(reservationRequestDTO, existingReservation);

        Reservation updatedReservation = reservationRepository.save(existingReservation);

        return reservationMapper.toResponseDTO(updatedReservation);
    }

    @Override
    public ReservationResponseDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with ID: " + id));
        return reservationMapper.toResponseDTO(reservation);
    }

    @Override
    public List<ReservationResponseDTO> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new EntityNotFoundException("Reservation not found with ID: " + id);
        }
        reservationRepository.deleteById(id);
    }


    @Override
    public boolean isDriverAvailable(long driverId, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime) {
        List<Reservation> existingReservations = reservationRepository.findByDriverIdAndTimeOverlap(driverId, reservationStartTime, reservationEndTime);
        return existingReservations.isEmpty();
    }


    @Override
    public boolean isVehicleAvailable(long vehicleId, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime) {
        List<Reservation> existingReservations = reservationRepository.findByVehicleIdAndTimeOverlap(vehicleId, reservationStartTime, reservationEndTime);
        return existingReservations.isEmpty();
    }


    @Override
    public boolean isReservationStatusValid(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
        return reservation.getStatus().equals("CONFIRMED") || reservation.getStatus().equals("COMPLETED");
    }

    @Override
    public ReservationAnalyticsDTO getAnalytics() {
        List<Reservation> reservations = reservationRepository.findAll();

        double averagePricePerKm = reservations.stream()
                .filter(r -> r.getDistanceKm() > 0)
                .mapToDouble(r -> r.getPrice() / r.getDistanceKm())
                .average()
                .orElse(0);

        double averageDistance = reservations.stream()
                .mapToDouble(Reservation::getDistanceKm)
                .average()
                .orElse(0);

        Map<String, Integer> reservationsByTimeSlot = reservations.stream()
                .collect(Collectors.groupingBy(
                        r -> getTimeSlot(r.getStartTime()), // Plage horaire
                        Collectors.summingInt(r -> 1)
                ));

        Map<Address, Integer> mostRequestedZones = reservations.stream()
                .collect(Collectors.groupingBy(
                        Reservation::getDepartureAddress, // Zone géographique
                        Collectors.summingInt(r -> 1)
                ));

        ReservationAnalyticsDTO dto = new ReservationAnalyticsDTO();
        dto.setAveragePricePerKm(averagePricePerKm);
        dto.setAverageDistance(averageDistance);
        dto.setReservationsByTimeSlot(reservationsByTimeSlot);
        dto.setMostRequestedZones(mostRequestedZones);

        return dto;
    }

    private String getTimeSlot(LocalDateTime time) {
        int hour = time.getHour();
        return hour + "h-" + (hour + 1) + "h";
    }
}
