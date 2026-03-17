package com.civica.splitthebill.infraestructure.adapter.in.rest.handler;

import java.util.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.civica.splitthebill.domain.exception.EntityAlreadyAssignedException;

/**
 * GlobalExceptionHandler
 *
 * Se encarga de capturar todas las excepciones lanzadas en los controllers
 * y devolver una respuesta estándar siguiendo RFC 7807 (Problem Details).
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Método helper para construir una respuesta estándar RFC 7807.
     */
    private ResponseEntity<Map<String, Object>> buildProblem(
            String type,                      
            String title,                     
            HttpStatus status,                
            String detail,                    
            String instance,                  
            Map<String, List<String>> errors  
    ) {

        Map<String, Object> problem = new HashMap<>();

        problem.put("type", type);                
        problem.put("title", title);              
        problem.put("status", status.value());    
        problem.put("detail", detail);            
        problem.put("instance", instance);        
        problem.put("errors", errors);            

        return ResponseEntity.status(status).body(problem);
    }

    /**
     * Manejo de errores de validación automática (@Valid en DTOs)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        Map<String, List<String>> errors = new HashMap<>();

        // Recorremos todos los errores de validación
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.computeIfAbsent(error.getField(), k -> new ArrayList<>())
                      .add(error.getDefaultMessage())
        );

        return buildProblem(
                "https://example.com/problems/validation-error",
                "Validation failed",
                HttpStatus.BAD_REQUEST,
                "There are validation errors",
                request.getRequestURI(),
                errors
        );
    }

    /**
     * Manejo de excepciones de negocio (dominio)
     */
    @ExceptionHandler({
        EntityAlreadyAssignedException.class
    })
    public ResponseEntity<Map<String, Object>> handleBusinessExceptions(
            RuntimeException ex,
            HttpServletRequest request
    ) {

        Map<String, List<String>> errors = new HashMap<>();

        String field = "unknown";

        // Extraemos el campo dependiendo del tipo de excepción
        if (ex instanceof ExceptionHandler e) {
            // field = e.getMessage();
        }

        errors.computeIfAbsent(field, k -> new ArrayList<>())
              .add(ex.getMessage());

        return buildProblem(
                "https://example.com/problems/business-error",
                "Business rule violation",
                HttpStatus.BAD_REQUEST,
                "There are business rule violations",
                request.getRequestURI(),
                errors
        );
    }

}