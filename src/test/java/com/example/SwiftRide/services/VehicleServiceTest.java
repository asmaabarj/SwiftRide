package com.example.SwiftRide.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.example.SwiftRide.dto.VehicleAnalyticsDTO;
import com.example.SwiftRide.dto.VehicleDTO;
import com.example.SwiftRide.models.Vehicle;
import com.example.SwiftRide.models.enums.*;
import com.example.SwiftRide.repositories.VehicleRepository;
import com.example.SwiftRide.services.impl.VehicleServiceImpl;
import com.example.SwiftRide.utils.VehicleMapper;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private Vehicle vehicle;
    private VehicleDTO vehicleDTO;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setModele("Toyota Camry");
        vehicle.setImmatriculation("ABC123");
        vehicle.setKilometrage(50000);
        vehicle.setStatut(AvailabilityStatus.AVAILABLE);
        vehicle.setType(VehiculeType.BERLINE);

        vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(1L);
        vehicleDTO.setModele("Toyota Camry");
        vehicleDTO.setImmatriculation("ABC123");
        vehicleDTO.setKilometrage(50000);
        vehicleDTO.setStatut(AvailabilityStatus.AVAILABLE);
        vehicleDTO.setType(VehiculeType.BERLINE);
    }

    @Test
    void createVehicle() {
        when(vehicleRepository.existsByImmatriculation(anyString())).thenReturn(false);
        when(vehicleMapper.toEntity(any(VehicleDTO.class))).thenReturn(vehicle);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);
        when(vehicleMapper.toDTO(any(Vehicle.class))).thenReturn(vehicleDTO);

        VehicleDTO result = vehicleService.createVehicle(vehicleDTO);

        assertNotNull(result);
        assertEquals(vehicleDTO.getImmatriculation(), result.getImmatriculation());
    }

    @Test
    void getVehicle() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleMapper.toDTO(vehicle)).thenReturn(vehicleDTO);

        VehicleDTO result = vehicleService.getVehicle(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getAllVehicle() {
        List<Vehicle> vehicles = Arrays.asList(vehicle);
        when(vehicleRepository.findAll()).thenReturn(vehicles);
        when(vehicleMapper.toDTO(vehicle)).thenReturn(vehicleDTO);

        List<VehicleDTO> result = vehicleService.getAllVehicles();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getVehiclesWithPagination() {
        Page<Vehicle> vehiclePage = new PageImpl<>(Arrays.asList(vehicle));
        when(vehicleRepository.findAll(any(PageRequest.class))).thenReturn(vehiclePage);
        when(vehicleMapper.toDTO(vehicle)).thenReturn(vehicleDTO);

        Page<VehicleDTO> result = vehicleService.getVehiclesWithPagination(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getAnalytics() {
        Object[] berlineKm = new Object[]{VehiculeType.BERLINE, 50000.0};
        Object[] berlineUtil = new Object[]{VehiculeType.BERLINE, 2L};
        Object[] fleetStatus = new Object[]{AvailabilityStatus.AVAILABLE, 5L};

        List<Object[]> kmData = Collections.singletonList(berlineKm);
        List<Object[]> utilizationData = Collections.singletonList(berlineUtil);
        List<Object[]> fleetData = Collections.singletonList(fleetStatus);

        when(vehicleRepository.getAverageKilometrageByType()).thenReturn(kmData);
        when(vehicleRepository.getUtilizationRateByType()).thenReturn(utilizationData);
        when(vehicleRepository.getFleetStatus()).thenReturn(fleetData);
        when(vehicleRepository.count()).thenReturn(10L);

        VehicleAnalyticsDTO result = vehicleService.getAnalytics();

        assertNotNull(result);
        assertNotNull(result.getKilometrageMoyenParType());
        assertNotNull(result.getTauxUtilisationParType());
        assertNotNull(result.getEtatFlotte());
        assertNotNull(result.getPrixParKmParType());

        assertEquals(50000.0, result.getKilometrageMoyenParType().get("BERLINE"));
        assertEquals(20.0, result.getTauxUtilisationParType().get("BERLINE"));
        assertEquals(5L, result.getEtatFlotte().get("AVAILABLE"));
        assertEquals(5.0, result.getPrixParKmParType().get("BERLINE"));
    }
} 