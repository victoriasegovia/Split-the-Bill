package com.civica.splitthebill.domain.exception;

/**
 * Thrown when attempting to create a group with a name that already exists.
 */
public class DuplicateGroupNameException extends RuntimeException {

    public DuplicateGroupNameException(String name) {
        super("Group with name '" + name + "' already exists");
    }
    
}
