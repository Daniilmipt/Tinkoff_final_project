package com.example.config;

import com.example.exception.AuthException;
import com.example.exception.InterruptThreadException;
import com.example.exception.ResponseErrorDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;


@RestControllerAdvice
public class TravelExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthException e){
        return ResponseErrorDto.getErrorResponse(
                "Class: " + e.getClass() + "; " + e.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e){
        return ResponseErrorDto.getErrorResponse(
                "Class: " + e.getClass() + "; " + e.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e){
        return ResponseErrorDto.getErrorResponse(
                "Class: " + e.getClass() + "; " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<Object> handleInterruptedException(InterruptedException e){
        return ResponseErrorDto.getErrorResponse(
                "Class: " + e.getClass() + "; " + e.getMessage() + ";\n Exception in thread calculation",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(ExecutionException.class)
    public ResponseEntity<Object> handleExecutionException(ExecutionException e){
        return ResponseErrorDto.getErrorResponse(
                "Class: " + e.getClass() + "; " + e.getMessage() + ";\n Exception in thread calculation",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(JsonProcessingException e){
        return ResponseErrorDto.getErrorResponse(
                "Error with json work",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(SQLException e){
        return ResponseErrorDto.getErrorResponse(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e){
        return ResponseErrorDto.getErrorResponse(
                e.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        return ResponseErrorDto.getErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InterruptThreadException.class)
    public ResponseEntity<Object> handleInterruptThreadException(InterruptThreadException e){
        return ResponseErrorDto.getErrorResponse(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<Object> handleRequestNotPermitted() {
        return ResponseErrorDto.getErrorResponse(
                "Too many requests in period",
                HttpStatus.TOO_MANY_REQUESTS
        );
    }
}
