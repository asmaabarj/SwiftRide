package com.example.SwiftRide.dto;

import com.example.SwiftRide.models.enums.AvailabilityStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuperBuilder
public class DriverDTO extends UserDTO{
    @NotNull(message = "status is required")
    private AvailabilityStatus status;

    @NotBlank(message = "availabilityStart is required")
    private LocalTime availabilityStart;

    @NotBlank(message = "availabilityEnd is required")
    private LocalTime availabilityEnd;
}
