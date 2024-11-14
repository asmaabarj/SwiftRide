package com.example.SwiftRide.dto;

import com.example.SwiftRide.models.enums.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DriverAnalyticsDTO {
    private double occupancyRate;
    private Map<AvailabilityStatus, Integer> statusDistribution;
    private Map<String, LocalTime[]> availabilityTimeSlots;
}
