package com.example.SwiftRide.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.SwiftRide.dto.VehicleDTO;
import com.example.SwiftRide.models.Vehicle;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VehicleMapper {
    private final ModelMapper modelMapper;

    public VehicleDTO toDTO(Vehicle vehicle) {
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    public Vehicle toEntity(VehicleDTO vehicleDTO) {
        return modelMapper.map(vehicleDTO, Vehicle.class);
    }
} 