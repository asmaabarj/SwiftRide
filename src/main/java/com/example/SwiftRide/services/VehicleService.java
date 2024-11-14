package com.example.SwiftRide.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.SwiftRide.dto.VehicleAnalyticsDTO;
import com.example.SwiftRide.dto.VehicleDTO;

public interface VehicleService {
    VehicleDTO createVehicle(VehicleDTO vehicleDTO);
    VehicleDTO getVehicle(Long id);
    Page<VehicleDTO> getAllVehicles(Pageable pageable);
    VehicleDTO updateVehicle(Long id, VehicleDTO vehicleDTO);
    void deleteVehicle(Long id);
}
