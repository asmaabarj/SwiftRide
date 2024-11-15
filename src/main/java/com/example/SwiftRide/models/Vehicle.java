package com.example.SwiftRide.models;

import javax.persistence.*;
import javax.persistence.EnumType;
import javax.persistence.GenerationType;
import javax.validation.constraints.*;

import com.example.SwiftRide.models.enums.AvailabilityStatus;
import com.example.SwiftRide.models.enums.VehiculeType;

import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;

    @NotBlank(message = "L'immatriculation est obligatoire")
    @Column(unique = true)
    private String immatriculation;

    @Min(value = 0, message = "Le kilométrage ne peut pas être négatif")
    private double kilometrage;

    @NotNull(message = "Le statut est obligatoire")
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus statut;

    @NotNull(message = "Le type est obligatoire")
    @Enumerated(EnumType.STRING)
    private VehiculeType type;
}