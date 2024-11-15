package com.example.SwiftRide.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.SwiftRide.dto.VehicleAnalyticsDTO;
import com.example.SwiftRide.dto.VehicleDTO;

public interface VehicleService {
    VehicleDTO createVehicle(VehicleDTO vehicleDTO);
    VehicleDTO getVehicle(Long id);
    List<VehicleDTO> getAllVehicles();
    VehicleDTO updateVehicle(Long id, VehicleDTO vehicleDTO);
    void deleteVehicle(Long id);

    VehicleAnalyticsDTO getAnalytics();

    Page<VehicleDTO> getVehiclesWithPagination(Pageable pageable);

}
