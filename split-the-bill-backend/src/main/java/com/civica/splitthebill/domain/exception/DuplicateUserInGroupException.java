package com.civica.splitthebill.domain.exception;

public class DuplicateUserInGroupException extends RuntimeException {

    public DuplicateUserInGroupException(Long userId, Long groupId) {
        super("User with id " + userId + " already exists in group wiht id" + groupId + ".");
    }
    
}
