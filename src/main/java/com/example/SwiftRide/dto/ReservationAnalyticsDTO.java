package com.example.SwiftRide.dto;

import com.example.SwiftRide.models.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ReservationAnalyticsDTO {
    private double averagePricePerKm;
    private double averageDistance;
    private Map<String, Integer> reservationsByTimeSlot;
    private Map<Address, Integer> mostRequestedZones;
}
