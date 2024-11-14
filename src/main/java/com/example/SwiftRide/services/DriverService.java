package com.example.SwiftRide.services;

import com.example.SwiftRide.dto.DriverDTO;

import java.util.List;
import java.util.Optional;

public interface DriverService {
    DriverDTO saveDriver(DriverDTO driverDTO);
    Optional<DriverDTO> getDriver(Long id);
    DriverDTO updateDriver(DriverDTO driverDTO);
    void deleteDriver(Long id);
    List<DriverDTO> getAllDriver();
}
