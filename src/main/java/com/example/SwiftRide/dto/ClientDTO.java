package com.example.SwiftRide.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuperBuilder
public class ClientDTO extends UserDTO{
    @NotBlank (message = "phoneNumber is required")
    @Size(min = 10, max = 10, message = "phoneNumber must be 10 characters")
    private String phoneNumber;
}
