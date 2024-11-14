package com.example.SwiftRide.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.SwiftRide.models.enums.AvailabilityStatus;
import com.example.SwiftRide.models.enums.VehiculeType;

import lombok.Data;

@Data
public class VehicleDTO {
    private Long id;
    
    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;
    
    @NotBlank(message = "L'immatriculation est obligatoire")
    private String immatriculation;
    
    @Min(value = 0, message = "Le kilométrage ne peut pas être négatif")
    private double kilometrage;
    
    @NotNull(message = "Le statut est obligatoire")
    private AvailabilityStatus statut;
    
    @NotNull(message = "Le type est obligatoire")
    private VehiculeType type;
}
