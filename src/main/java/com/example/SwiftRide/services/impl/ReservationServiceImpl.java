package com.example.SwiftRide.services.impl;

import com.example.SwiftRide.dto.ReservationDTO;
import com.example.SwiftRide.models.Reservation;
import com.example.SwiftRide.models.enums.ReservationStatus;
import com.example.SwiftRide.repositories.ReservationRepository;
import com.example.SwiftRide.services.ReservationService;
import com.example.SwiftRide.utils.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;


    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository,ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::reservationToReservationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationDTO> getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::reservationToReservationDTO);
    }

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = reservationMapper.reservationDTOToReservation(reservationDTO);
        if (reservation.getStatus() == null) {
            reservation.setStatus(ReservationStatus.CREATED);
        }
        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.reservationToReservationDTO(savedReservation);
    }

    @Override
    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id " + id));

        Reservation updatedReservation = reservationMapper.reservationDTOToReservation(reservationDTO);

        updatedReservation.setId(reservation.getId());
        reservationRepository.save(updatedReservation);

        return reservationMapper.reservationToReservationDTO(updatedReservation);
    }

    @Override
    public void deleteReservation(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            reservationRepository.delete(reservation.get());
        } else {
            throw new RuntimeException("Reservation not found with id " + id);
        }
    }
}
