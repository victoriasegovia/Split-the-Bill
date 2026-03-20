package com.civica.splitthebill.domain.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException (Long entityId) {
        super("Entity with id " + entityId + " already exists.");
    }
}
