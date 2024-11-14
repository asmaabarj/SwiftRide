package com.example.SwiftRide.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.SwiftRide.dto.VehicleAnalyticsDTO;
import com.example.SwiftRide.dto.VehicleDTO;
import com.example.SwiftRide.exceptions.AlreadyExistsException;
import com.example.SwiftRide.exceptions.DoesNotExistsException;
import com.example.SwiftRide.models.Vehicle;
import com.example.SwiftRide.models.enums.AvailabilityStatus;
import com.example.SwiftRide.repositories.VehicleRepository;
import com.example.SwiftRide.services.VehicleService;
import com.example.SwiftRide.utils.VehicleMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        log.debug("Création d'un nouveau véhicule : {}", vehicleDTO);
        if (vehicleRepository.existsByImmatriculation(vehicleDTO.getImmatriculation())) {
            throw new AlreadyExistsException("Un véhicule avec cette immatriculation existe déjà");
        }
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        return vehicleMapper.toDTO(vehicleRepository.save(vehicle));
    }

    @Override
    public VehicleDTO getVehicle(Long id) {
        log.debug("Recherche du véhicule avec l'ID : {}", id);
        return vehicleMapper.toDTO(vehicleRepository.findById(id)
                .orElseThrow(() -> new DoesNotExistsException("Véhicule non trouvé")));
    }

    @Override
    public Page<VehicleDTO> getAllVehicles(Pageable pageable) {
        log.debug("Récupération de tous les véhicules");
        return vehicleRepository.findAll(pageable).map(vehicleMapper::toDTO);
    }

    @Override
    public VehicleDTO updateVehicle(Long id, VehicleDTO vehicleDTO) {
        log.debug("Mise à jour du véhicule : {}", vehicleDTO);
        if (!vehicleRepository.existsById(id)) {
            throw new DoesNotExistsException("Véhicule non trouvé");
        }
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        vehicle.setId(id);
        return vehicleMapper.toDTO(vehicleRepository.save(vehicle));
    }

    @Override
    public void deleteVehicle(Long id) {
        log.debug("Suppression du véhicule avec l'ID : {}", id);
        if (!vehicleRepository.existsById(id)) {
            throw new DoesNotExistsException("Véhicule non trouvé");
        }
        vehicleRepository.deleteById(id);
    }


}
