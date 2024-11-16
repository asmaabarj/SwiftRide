package com.example.SwiftRide.services;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.SwiftRide.dto.VehicleAnalyticsDTO;
import com.example.SwiftRide.dto.VehicleDTO;
import com.example.SwiftRide.models.Vehicle;
import com.example.SwiftRide.models.enums.AvailabilityStatus;
import com.example.SwiftRide.models.enums.VehiculeType;
import com.example.SwiftRide.repositories.VehicleRepository;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class VehicleServiceIntegrationTest {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    void createVehicle() {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setModele("Toyota Camry");
        vehicleDTO.setImmatriculation("TEST123");
        vehicleDTO.setKilometrage(50000);
        vehicleDTO.setStatut(AvailabilityStatus.AVAILABLE);
        vehicleDTO.setType(VehiculeType.BERLINE);

        VehicleDTO result = vehicleService.createVehicle(vehicleDTO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("TEST123", result.getImmatriculation());
    }

    @Test
    void getVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setModele("Toyota Camry");
        vehicle.setImmatriculation("TEST456");
        vehicle.setKilometrage(60000);
        vehicle.setStatut(AvailabilityStatus.AVAILABLE);
        vehicle.setType(VehiculeType.BERLINE);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        VehicleDTO result = vehicleService.getVehicle(savedVehicle.getId());

        assertNotNull(result);
        assertEquals("TEST456", result.getImmatriculation());
    }

    @Test
    void getAllVehicles() {
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setModele("Toyota Camry");
        vehicle1.setImmatriculation("TEST789");
        vehicle1.setKilometrage(70000);
        vehicle1.setStatut(AvailabilityStatus.AVAILABLE);
        vehicle1.setType(VehiculeType.BERLINE);
        vehicleRepository.save(vehicle1);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setModele("Mercedes Sprinter");
        vehicle2.setImmatriculation("TEST101");
        vehicle2.setKilometrage(80000);
        vehicle2.setStatut(AvailabilityStatus.ON_TRIP);
        vehicle2.setType(VehiculeType.VAN);
        vehicleRepository.save(vehicle2);

        List<VehicleDTO> results = vehicleService.getAllVehicles();

        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    void updateVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setModele("Toyota Camry");
        vehicle.setImmatriculation("TEST202");
        vehicle.setKilometrage(90000);
        vehicle.setStatut(AvailabilityStatus.AVAILABLE);
        vehicle.setType(VehiculeType.BERLINE);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        VehicleDTO updateDTO = new VehicleDTO();
        updateDTO.setId(savedVehicle.getId());
        updateDTO.setModele("Toyota Corolla");
        updateDTO.setImmatriculation("TEST202");
        updateDTO.setKilometrage(95000);
        updateDTO.setStatut(AvailabilityStatus.ON_TRIP);
        updateDTO.setType(VehiculeType.BERLINE);

        VehicleDTO result = vehicleService.updateVehicle(savedVehicle.getId(), updateDTO);

        assertNotNull(result);
        assertEquals("Toyota Corolla", result.getModele());
        assertEquals(95000, result.getKilometrage());
    }

    @Test
    void deleteVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setModele("Toyota Camry");
        vehicle.setImmatriculation("TEST303");
        vehicle.setKilometrage(100000);
        vehicle.setStatut(AvailabilityStatus.AVAILABLE);
        vehicle.setType(VehiculeType.BERLINE);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        vehicleService.deleteVehicle(savedVehicle.getId());

        assertFalse(vehicleRepository.existsById(savedVehicle.getId()));
    }

    @Test
    void getAnalytics() {
        createTestVehicles();

        VehicleAnalyticsDTO result = vehicleService.getAnalytics();

        assertNotNull(result);
        assertNotNull(result.getKilometrageMoyenParType());
        assertNotNull(result.getTauxUtilisationParType());
        assertNotNull(result.getEtatFlotte());
        assertNotNull(result.getPrixParKmParType());
    }

    @Test
    void getPaginatedVehicles() {
        createTestVehicles();

        Page<VehicleDTO> result = vehicleService.getVehiclesWithPagination(PageRequest.of(0, 2));

        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0);
        assertEquals(2, result.getSize());
    }

    private void createTestVehicles() {
        Vehicle v1 = new Vehicle();
        v1.setModele("Toyota");
        v1.setImmatriculation("TEST001");
        v1.setKilometrage(50000);
        v1.setStatut(AvailabilityStatus.ON_TRIP);
        v1.setType(VehiculeType.BERLINE);
        vehicleRepository.save(v1);

        Vehicle v2 = new Vehicle();
        v2.setModele("Mercedes");
        v2.setImmatriculation("TEST002");
        v2.setKilometrage(60000);
        v2.setStatut(AvailabilityStatus.AVAILABLE);
        v2.setType(VehiculeType.VAN);
        vehicleRepository.save(v2);
    }
} 