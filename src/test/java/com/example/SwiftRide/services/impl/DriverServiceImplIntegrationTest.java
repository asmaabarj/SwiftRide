package com.example.SwiftRide.services.impl;

import com.example.SwiftRide.SwiftRideApplication;
import com.example.SwiftRide.dto.DriverDTO;
import com.example.SwiftRide.models.Driver;
import com.example.SwiftRide.models.enums.AvailabilityStatus;
import com.example.SwiftRide.repositories.DriverRepository;
import com.example.SwiftRide.services.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SwiftRideApplication.class)
@Transactional
class DriverServiceImplIntegrationTest {
     @Autowired
     private DriverRepository driverRepository;

     @Autowired
     private DriverService driverService;

     @Autowired
     private ModelMapper modelMapper;

     private Driver driver;
     private DriverDTO driverDTO;

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
     }

     @Test
      void testSaveDriver() {
            driverService.saveDriver(driverDTO);
            Driver savedDriver = driverRepository.findById(driverDTO.getId()).get();
            assertEquals(driverDTO.getId(), savedDriver.getId());
     }

     @Test
     void testGetDriver() {
            driverService.saveDriver(driverDTO);
            DriverDTO savedDriver = driverService.getDriver(driverDTO.getId()).get();
            assertEquals(driverDTO.getId(), savedDriver.getId());
     }

     @Test
     void testUpdateDriver() {
            driverService.saveDriver(driverDTO);
            driverDTO.setFname("Jane");
            driverService.updateDriver(driverDTO);
            DriverDTO updatedDriver = driverService.getDriver(driverDTO.getId()).get();
            assertEquals(driverDTO.getFname(), updatedDriver.getFname());
     }

     @Test
     void testDeleteDriver() {
           driverService.saveDriver(driverDTO);
           driverService.deleteDriver(driverDTO.getId());
           assertEquals(0, driverRepository.count());
     }

     @Test
     void testGetDriverAnalytics() {
           driverService.saveDriver(driverDTO);
           DriverDTO savedDriver = driverService.getDriver(driverDTO.getId()).get();
           assertEquals(driverDTO.getId(), savedDriver.getId());
     }

     @Test
     void testGetAllDriver() {
            driverService.saveDriver(driverDTO);
            assertEquals(1, driverService.getAllDriver().size());
     }
}
