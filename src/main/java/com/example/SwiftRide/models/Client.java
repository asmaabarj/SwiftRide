package com.example.SwiftRide.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity(name = "Clients")
@DiscriminatorValue("Client")
public class Client extends User{

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;
}
