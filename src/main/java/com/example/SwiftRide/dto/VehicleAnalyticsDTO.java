package com.example.SwiftRide.dto;

import java.util.Map;

import lombok.Data;

@Data
public class VehicleAnalyticsDTO {
    private Map<String, Double> kilometrageMoyenParType;
    private Map<String, Double> tauxUtilisationParType;
    private Map<String, Long> etatFlotte;
    private Map<String, Double> prixParKmParType;
}
