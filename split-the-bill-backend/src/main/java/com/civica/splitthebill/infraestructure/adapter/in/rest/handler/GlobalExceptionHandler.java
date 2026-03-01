package com.civica.splitthebill.infraestructure.adapter.in.rest.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.civica.splitthebill.domain.exception.DuplicateGroupNameException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateGroupNameException.class)
    public ResponseEntity<String> handleDuplicateGroup(DuplicateGroupNameException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
    
}
