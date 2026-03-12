package com.civica.splitthebill.domain.exception;

public class DuplicateExpenseInGroupException extends RuntimeException {
    
    public DuplicateExpenseInGroupException(Long expenseId, Long groupId) {
        super("Expense with id " + expenseId + " already exists in group wiht id" + groupId + ".");
    }

}
