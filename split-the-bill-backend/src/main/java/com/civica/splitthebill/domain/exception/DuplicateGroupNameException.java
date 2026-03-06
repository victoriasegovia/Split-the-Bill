package com.civica.splitthebill.domain.exception;

public class DuplicateGroupNameException extends RuntimeException {

    public DuplicateGroupNameException(String name) {
        super("Group with name '" + name + "' already exists.");
    }
    
}
