package com.example.SwiftRide.services.impl;

import com.example.SwiftRide.dto.DriverAnalyticsDTO;
import com.example.SwiftRide.dto.DriverDTO;
import com.example.SwiftRide.models.Driver;
import com.example.SwiftRide.models.enums.AvailabilityStatus;
import com.example.SwiftRide.repositories.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class DriverServiceImplTest {
    @Mock
    private DriverRepository driverRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DriverServiceImpl driverService;

    private Driver driver;
    private DriverDTO driverDTO;
    private DriverAnalyticsDTO driverAnalyticsDTO;

    @BeforeEach
    public void init() {
        driverDTO = DriverDTO.builder()
                .id(1L)
                .fname("John")
                .lname("Doe")
                .email("John@gmail.com")
                .idc("AD12345")
                .status(AvailabilityStatus.ON_TRIP)
                .availabilityStart(LocalTime.NOON)
                .availabilityEnd(LocalTime.MIDNIGHT)
                .build();

        driver = Driver.builder()
                .id(1L)
                .fname("John")
                .lname("Doe")
                .email("John@gmail.com")
                .idc("AD12345")
                .status(AvailabilityStatus.ON_TRIP)
                .availabilityStart(LocalTime.NOON)
                .availabilityEnd(LocalTime.MIDNIGHT)
                .build();

        driverAnalyticsDTO = DriverAnalyticsDTO.builder()
                .occupancyRate(0.0)
                .statusDistribution(Collections.emptyMap())
                .availabilityTimeSlots(Collections.emptyMap()).build();
    }

    @Test
    @DisplayName("test save driver")
    void testSaveDriver() {
        given(driverRepository.existsById(driverDTO.getId())).willReturn(false);
        given(modelMapper.map(driverDTO, Driver.class)).willReturn(driver);
        given(driverRepository.save(driver)).willReturn(driver);
        given(modelMapper.map(driver, DriverDTO.class)).willReturn(driverDTO);

        DriverDTO savedDriver = driverService.saveDriver(driverDTO);
        assertEquals(driverDTO, savedDriver);
    }

    @Test
    @DisplayName("test get driver analytics")
    void testGetDriverAnalytics() {
        DriverAnalyticsDTO result = driverService.getDriverAnalytics();
        assertEquals(driverAnalyticsDTO, result);
    }

    @Test
    @DisplayName("test get driver")
    void testGetDriver() {
        given(driverRepository.existsById(driverDTO.getId())).willReturn(true);
        given(driverRepository.findById(driverDTO.getId())).willReturn(java.util.Optional.of(driver));
        given(modelMapper.map(driver, DriverDTO.class)).willReturn(driverDTO);

        DriverDTO result = driverService.getDriver(1L).get();
        assertEquals(driverDTO, result);
    }

    @Test
    @DisplayName("test update driver")
    void testUpdateDriver() {
        given(driverRepository.existsById(driverDTO.getId())).willReturn(true);
        given(modelMapper.map(driverDTO, Driver.class)).willReturn(driver);
        given(driverRepository.save(driver)).willReturn(driver);
        given(modelMapper.map(driver, DriverDTO.class)).willReturn(driverDTO);

        DriverDTO result = driverService.updateDriver(driverDTO);
        assertEquals(driverDTO, result);
    }

    @Test
    @DisplayName("test delete driver")
    void testDeleteDriver() {
        given(driverRepository.existsById(1L)).willReturn(true);
        driverService.deleteDriver(1L);
    }

    @Test
    @DisplayName("test get all drivers")
    void testGetAllDrivers() {
        given(driverRepository.findAll()).willReturn(Collections.singletonList(driver));
        given(modelMapper.map(driver, DriverDTO.class)).willReturn(driverDTO);

        assertEquals(Collections.singletonList(driverDTO), driverService.getAllDriver());
    }
}
