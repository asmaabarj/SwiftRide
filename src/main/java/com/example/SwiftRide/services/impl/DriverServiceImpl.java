package com.example.SwiftRide.services.impl;

import com.example.SwiftRide.dto.*;
import com.example.SwiftRide.exceptions.*;
import com.example.SwiftRide.models.Driver;
import com.example.SwiftRide.models.enums.AvailabilityStatus;
import com.example.SwiftRide.repositories.DriverRepository;
import com.example.SwiftRide.services.DriverService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@AllArgsConstructor
@Slf4j
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;

    @Override
    public DriverDTO saveDriver(DriverDTO driverDTO) {
        if (driverRepository.existsById(driverDTO.getId())) {
            throw new AlreadyExistsException("Driver with id " + driverDTO.getId() + " already exists");
        }else {
            Driver driver = modelMapper.map(driverDTO, Driver.class);
            driver = driverRepository.save(driver);
            return modelMapper.map(driver, DriverDTO.class);
        }
    }

    @Override
    public Optional<DriverDTO> getDriver(Long id) {
        if (driverRepository.existsById(id)) {
            Driver driver = driverRepository.findById(id).get();
            return Optional.of(modelMapper.map(driver, DriverDTO.class));
        }else {
            return Optional.empty();
        }
    }

    @Override
    public DriverDTO updateDriver(DriverDTO driverDTO) {
        if (driverRepository.existsById(driverDTO.getId())) {
            Driver driver = modelMapper.map(driverDTO, Driver.class);
            driver = driverRepository.save(driver);
            return modelMapper.map(driver, DriverDTO.class);
        }else {
            throw new DoesNotExistsException("Driver with id " + driverDTO.getId() + " does not exist");
        }
    }

    @Override
    public void deleteDriver(Long id) {
        if (driverRepository.existsById(id)) {
            driverRepository.deleteById(id);
        }else {
            throw new DoesNotExistsException("Driver with id " + id + " does not exist");
        }
    }

    @Override
    public List<DriverDTO> getAllDriver() {
        return driverRepository.findAll().stream().map(driver -> modelMapper.map(driver, DriverDTO.class)).collect(Collectors.toList());
    }

    @Override
    public DriverAnalyticsDTO getDriverAnalytics() {
        List<Driver> drivers = driverRepository.findAll();

        double occupancyRate = calculateOccupancyRate(drivers);

        Map<AvailabilityStatus, Integer> statusDistribution = calculateStatusDistribution(drivers);

        Map<String, LocalTime[]> availabilityTimeSlots = calculateAvailabilityTimeSlots(drivers);

        return new DriverAnalyticsDTO(occupancyRate, statusDistribution, availabilityTimeSlots);
    }


    private double calculateOccupancyRate(List<Driver> drivers) {
        long totalDrivers = drivers.size();
        long driversOnTrip = drivers.stream()
                .filter(driver -> driver.getStatus() == AvailabilityStatus.ON_TRIP)
                .count();

        return totalDrivers == 0 ? 0 : (double) driversOnTrip / totalDrivers * 100;
    }

    private Map<AvailabilityStatus, Integer> calculateStatusDistribution(List<Driver> drivers) {
        Map<AvailabilityStatus, Integer> statusDistribution = new HashMap<>();
        drivers.forEach(driver -> {
            statusDistribution.merge(driver.getStatus(), 1, Integer::sum);
        });
        return statusDistribution;
    }

    private Map<String, LocalTime[]> calculateAvailabilityTimeSlots(List<Driver> drivers) {
        Map<String, LocalTime[]> availabilityTimeSlots = new HashMap<>();

        drivers.forEach(driver -> {
            String driverName = driver.getFname() + " " + driver.getLname();
            LocalTime[] availability = {driver.getAvailabilityStart(), driver.getAvailabilityEnd()};
            availabilityTimeSlots.put(driverName, availability);
        });

        return availabilityTimeSlots;
    }
}
