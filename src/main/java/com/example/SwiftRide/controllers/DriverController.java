package com.example.SwiftRide.controllers;

import com.example.SwiftRide.exceptions.DoesNotExistsException;
import com.example.SwiftRide.services.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.SwiftRide.dto.DriverDTO;
import com.example.SwiftRide.dto.DriverAnalyticsDTO;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/drivers")
public class DriverController {
    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<DriverDTO> createDriver(@Valid @RequestBody DriverDTO driverDTO) {
        DriverDTO savedDriver = driverService.saveDriver(driverDTO);
        return ResponseEntity.ok(savedDriver);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> getDriver(@PathVariable Long id) {
        Optional<DriverDTO> driverDTO = driverService.getDriver(id);
        return driverDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDTO> updateDriver(@PathVariable Long id,@Valid @RequestBody DriverDTO driverDTO) {
        if (!driverDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            DriverDTO updatedDriver = driverService.updateDriver(driverDTO);
            return ResponseEntity.ok(updatedDriver);
        } catch (DoesNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        try {
            driverService.deleteDriver(id);
            return ResponseEntity.noContent().build();
        } catch (DoesNotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<DriverDTO>> getAllDrivers() {
        List<DriverDTO> drivers = driverService.getAllDriver();
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/analytics")
    public ResponseEntity<DriverAnalyticsDTO> getDriverAnalytics() {
        DriverAnalyticsDTO driverAnalytics = driverService.getDriverAnalytics();
        return ResponseEntity.ok(driverAnalytics);
    }
}
