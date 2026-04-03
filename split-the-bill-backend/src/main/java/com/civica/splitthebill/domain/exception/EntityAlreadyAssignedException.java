package com.civica.splitthebill.domain.exception;

public class EntityAlreadyAssignedException extends RuntimeException {
    
    public EntityAlreadyAssignedException (Long whoId, String who, Long whereId, String where) {
        super(who + " with id " + whoId + " already exists in " + where + " with id " + whereId + ".");
    }
}
