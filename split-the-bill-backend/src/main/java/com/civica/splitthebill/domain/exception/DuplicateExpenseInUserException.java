package com.civica.splitthebill.domain.exception;

public class DuplicateExpenseInUserException extends RuntimeException {
    
    public DuplicateExpenseInUserException (Long expenseId, Long userId) {
        super("Expense with id " + expenseId + " already exists in user wiht id" + userId + ".");
    }

}
