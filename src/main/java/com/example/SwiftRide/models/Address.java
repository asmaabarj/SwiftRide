package com.example.SwiftRide.models;

import lombok.Data;
import javax.persistence.Embeddable;

@Embeddable
@Data
class Address {
    private String city;

    private String district;
}
