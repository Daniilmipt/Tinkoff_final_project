package com.example.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record ResponseErrorDto(
        String error,
        LocalDateTime timestamp
) {
    public static ResponseEntity<Object> getErrorResponse(String message, HttpStatus status){
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(
                message,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseErrorDto, status);
    }
}