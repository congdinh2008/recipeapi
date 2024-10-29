package com.congdinh.recipeapi.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.congdinh.recipeapi.dto.response.ResponseError;
import com.congdinh.recipeapi.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        var responseError = new ResponseError(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        var responseError = new ResponseError(e.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }
}
