package com.civica.splitthebill.domain.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id, String entity) {
        super(entity + " with id " + id + " not found.");
    }
}
