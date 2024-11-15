package com.example.SwiftRide.repositories;

import com.example.SwiftRide.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.driver.id = :driverId " +
            "AND r.startTime < :endTime " +
            "AND r.endTime > :startTime")
    List<Reservation> findByDriverIdAndTimeOverlap(@Param("driverId") Long driverId,
                                                   @Param("startTime") LocalDateTime startTime,
                                                   @Param("endTime") LocalDateTime endTime);

    @Query("SELECT r FROM Reservation r WHERE r.vehicle.id = :vehicleId " +
            "AND r.startTime < :endTime " +
            "AND r.endTime > :startTime")
    List<Reservation> findByVehicleIdAndTimeOverlap(@Param("vehicleId") Long vehicleId,
                                                    @Param("startTime") LocalDateTime startTime,
                                                    @Param("endTime") LocalDateTime endTime);
}

