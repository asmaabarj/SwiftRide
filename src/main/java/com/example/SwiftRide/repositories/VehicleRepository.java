package com.example.SwiftRide.repositories;

import com.example.SwiftRide.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByImmatriculation(String immatriculation);
    
    @Query("SELECT v.type, AVG(v.kilometrage) FROM Vehicle v GROUP BY v.type")
    List<Object[]> getAverageKilometrageByType();
    
    @Query("SELECT v.type, COUNT(v) FROM Vehicle v WHERE v.statut = 'EN_COURSE' GROUP BY v.type")
    List<Object[]> getUtilizationRateByType();
    
    @Query("SELECT v.statut, COUNT(v) FROM Vehicle v GROUP BY v.statut")
    List<Object[]> getFleetStatus();
}
