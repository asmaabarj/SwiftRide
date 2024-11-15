package com.example.SwiftRide.services.impl;

import java.util.Arrays;
import java.util.List;
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
import com.example.SwiftRide.models.enums.VehiculeType;
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
    public List<VehicleDTO> getAllVehicles() {
        log.debug("Récupération de tous les véhicules");
        return vehicleRepository.findAll().stream()
                .map(vehicleMapper::toDTO)
                .collect(Collectors.toList());
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


    @Override
    public VehicleAnalyticsDTO getAnalytics() {
        VehicleAnalyticsDTO analytics = new VehicleAnalyticsDTO();

        Map<String, Double> kmMoyen = vehicleRepository.getAverageKilometrageByType().stream()
                .collect(Collectors.toMap(
                        row -> ((VehiculeType) row[0]).name(),
                        row -> (Double) row[1]
                ));
        analytics.setKilometrageMoyenParType(kmMoyen);

        Map<String, Double> prixParKm = Arrays.stream(VehiculeType.values())
                .collect(Collectors.toMap(
                        VehiculeType::name,
                        VehiculeType::getPricePerKm
                ));
        analytics.setPrixParKmParType(prixParKm);

        Map<String, Double> tauxUtilisation = vehicleRepository.getUtilizationRateByType().stream()
                .collect(Collectors.toMap(
                        row -> ((VehiculeType) row[0]).name(),
                        row -> ((Long) row[1]) * 100.0 / vehicleRepository.count()
                ));
        analytics.setTauxUtilisationParType(tauxUtilisation);

        Map<String, Long> etatFlotte = vehicleRepository.getFleetStatus().stream()
                .collect(Collectors.toMap(
                        row -> ((AvailabilityStatus) row[0]).name(),
                        row -> (Long) row[1]
                ));
        analytics.setEtatFlotte(etatFlotte);

        return analytics;
    }

    @Override
    public Page<VehicleDTO> getVehiclesWithPagination(Pageable pageable) {
        Page<Vehicle> vehiclePage = vehicleRepository.findAll(pageable);
        return vehiclePage.map(vehicleMapper::toDTO);
    }




}