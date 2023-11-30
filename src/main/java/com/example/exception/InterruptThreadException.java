package com.example.exception;

import lombok.Getter;

@Getter
public class InterruptThreadException extends RuntimeException{
    private final String message;
    public InterruptThreadException(String message) {
        super(message);
        this.message = message;
    }
}
