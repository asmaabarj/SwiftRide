package com.example.SwiftRide.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.SwiftRide.models.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByImmatriculation(String immatriculation);

}
