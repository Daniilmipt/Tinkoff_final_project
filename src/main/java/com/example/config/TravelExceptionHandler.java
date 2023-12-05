package com.example.config;

import com.example.exception.AuthException;
import com.example.exception.InterruptThreadException;
import com.example.exception.ResponseErrorDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;


@RestControllerAdvice
public class TravelExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthException e){
        return new ResponseEntity<>(
                new ResponseErrorDto(
                        "Class: " + e.getClass() + "; " + e.getMessage(),
                        LocalDateTime.now()
                ),
                HttpStatus.FORBIDDEN
        );

    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e){
        return new ResponseEntity<>(
                new ResponseErrorDto(
                        "Class: " + e.getClass() + "; " + e.getMessage(),
                        LocalDateTime.now()
                ),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e){
        return new ResponseEntity<>(
                new ResponseErrorDto(
                        "Class: " + e.getClass() + "; " + e.getMessage(),
                        LocalDateTime.now()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<Object> handleInterruptedException(InterruptedException e){
        return new ResponseEntity<>(
                new ResponseErrorDto(
                        "Class: " + e.getClass() + "; " + e.getMessage() + ";\n Exception in thread calculation",
                        LocalDateTime.now()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(ExecutionException.class)
    public ResponseEntity<Object> handleExecutionException(ExecutionException e){
        return new ResponseEntity<>(
                new ResponseErrorDto(
                        "Class: " + e.getClass() + "; " + e.getMessage() + ";\n Exception in thread calculation",
                        LocalDateTime.now()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(){
        return new ResponseEntity<>(
                new ResponseErrorDto(
                        "Ошибка при работе с Json",
                        LocalDateTime.now()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(SQLException e){
        return new ResponseEntity<>(
                new ResponseErrorDto(
                        e.getMessage(),
                        LocalDateTime.now()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e){
        return new ResponseEntity<>(
                new ResponseErrorDto(
                        e.getMessage(),
                        LocalDateTime.now()
                ),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        return new ResponseEntity<>(
                new ResponseErrorDto(
                        e.getMessage(),
                        LocalDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InterruptThreadException.class)
    public ResponseEntity<Object> handleInterruptThreadException(InterruptThreadException e){
        return new ResponseEntity<>(
                new ResponseErrorDto(
                        e.getMessage(),
                        LocalDateTime.now()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<Object> handleRequestNotPermitted() {
        return new ResponseEntity<>(
                new ResponseErrorDto(
                        "Превышен лимит запросов за период",
                        LocalDateTime.now()
                ),
                HttpStatus.TOO_MANY_REQUESTS
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameterException() {
        return new ResponseEntity<>(
                new ResponseErrorDto(
                        "Неправильное значение параметра. Проверьте передается ли оно",
                        LocalDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException() {
        return new ResponseEntity<>(
                new ResponseErrorDto(
                        "Неправильное значение параметра",
                        LocalDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<Object> handleHttpMessageConversionException(HttpMessageConversionException e) {
        return new ResponseEntity<>(
                new ResponseErrorDto(
                        e.getMessage(),
                        LocalDateTime.now()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
