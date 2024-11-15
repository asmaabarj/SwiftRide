package com.example.SwiftRide.repositories;

import com.example.SwiftRide.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByDriverIdAndStartTimeBeforeAndEndTimeAfter(Long driverId, LocalDateTime startTime, LocalDateTime endTime);

    List<Reservation> findByVehicleIdAndStartTimeBeforeAndEndTimeAfter(Long vehicleId, LocalDateTime startTime, LocalDateTime endTime);
}
