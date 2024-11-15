package com.example.SwiftRide.models.enums;

public enum VehiculeType {
    BERLINE(5.0),
    VAN(7.0),
    MINIBUS(9.0);

    private final double pricePerKm;

    VehiculeType(double pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public double getPricePerKm() {
        return pricePerKm;
    }
}