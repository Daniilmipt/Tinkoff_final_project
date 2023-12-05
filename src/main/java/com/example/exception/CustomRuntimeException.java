package com.example.exception;

public class CustomRuntimeException extends RuntimeException {
    private final String message;
    public CustomRuntimeException(String message) {
        super(message);
        this.message = message;
    }
}
