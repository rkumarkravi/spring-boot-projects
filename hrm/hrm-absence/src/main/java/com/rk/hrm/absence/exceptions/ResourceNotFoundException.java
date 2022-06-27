package com.rk.hrm.absence.exceptions;

public class ResourceNotFoundException extends Exception {
    String message;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
