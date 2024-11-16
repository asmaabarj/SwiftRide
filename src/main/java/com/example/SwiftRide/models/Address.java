package com.example.SwiftRide.models;

import lombok.Data;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {
    private String city;

    private String district;
}
