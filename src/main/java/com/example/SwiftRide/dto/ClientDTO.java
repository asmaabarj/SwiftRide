package com.example.SwiftRide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class ClientDTO extends UserDTO{
    @NotBlank (message = "phoneNumber is required")
    @Size(min = 10, max = 10, message = "phoneNumber must be 10 characters")
    private String phoneNumber;
}
