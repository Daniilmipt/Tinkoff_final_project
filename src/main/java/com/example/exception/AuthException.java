package com.example.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException{
    private final String message;
    public AuthException(String message) {
        super(message);
        this.message = message;
    }
}
