package com.capgemini.hotelapi.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s não encontrado com ID: %d", resourceName, id));
    }
}
