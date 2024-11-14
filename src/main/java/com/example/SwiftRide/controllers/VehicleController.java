package com.example.SwiftRide.controllers;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.SwiftRide.dto.VehicleDTO;
import com.example.SwiftRide.services.VehicleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleDTO> createVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        log.info("Création d'un nouveau véhicule");
        return new ResponseEntity<>(vehicleService.createVehicle(vehicleDTO),
                HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Long id) {
        log.info("Récupération du véhicule avec l'ID: {}", id);
        return ResponseEntity.ok(vehicleService.getVehicle(id));
    }

    @GetMapping
    public ResponseEntity<Page<VehicleDTO>> getAllVehicles(Pageable pageable) {
        log.info("Récupération de tous les véhicules");
        return ResponseEntity.ok(vehicleService.getAllVehicles(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleDTO vehicleDTO) {
        log.info("Mise à jour du véhicule avec l'ID: {}", id);
        return ResponseEntity.ok(vehicleService.updateVehicle(id, vehicleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        log.info("Suppression du véhicule avec l'ID: {}", id);
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

}
