package com.example.SwiftRide.exceptions;

// Exception to be thrown when a resource already exists

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
