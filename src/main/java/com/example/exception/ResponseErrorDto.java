package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseErrorDto{
    private String error;
    private LocalDateTime timestamp;

    public static ResponseEntity<Object> getErrorResponse(String message, HttpStatus status){
        return new ResponseEntity<>(new ResponseErrorDto(message, LocalDateTime.now()), status);
    }
}
