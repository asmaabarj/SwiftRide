package com.example.SwiftRide.dto;

import com.example.SwiftRide.models.enums.AvailabilityStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class DriverDTO extends UserDTO{
    @NotBlank(message = "status is required")
    private AvailabilityStatus status;

    @NotBlank(message = "availabilityStart is required")
    private LocalDateTime availabilityStart;

    @NotBlank(message = "availabilityEnd is required")
    private LocalDateTime availabilityEnd;
}
