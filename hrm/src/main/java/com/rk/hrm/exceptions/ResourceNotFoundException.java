package com.rk.hrm.exceptions;

public class ResourceNotFoundException extends Exception {
    String message;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
