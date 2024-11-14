package com.example.SwiftRide.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class UserDTO {
    @Builder.Default
    private Long id = 0L;

    @NotBlank(message = "fname is required")
    @Size(min = 3, max = 25, message = "fname must be between 3 and 25 characters")
    private String fname;

    @NotBlank(message = "lname is required")
    @Size(min = 3, max = 25, message = "lname must be between 3 and 25 characters")
    private String lname;

    @NotBlank(message = "email is required")
    @Email(message = "email must be a valid email address")
    private String email;

    @NotBlank(message = "idc is required")
    @Size(min = 7, max = 8, message = "idc must be 8 characters")
    private String idc;
}

