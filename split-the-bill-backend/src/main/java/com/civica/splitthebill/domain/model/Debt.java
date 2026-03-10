package com.civica.splitthebill.domain.model;
/**
public record Debt (

    Long id,
    Long fromUserId,
    Long toUserId,
    Long expenseId,
    Long groupId,

    double owedAmount,
    double paidAmount

){
    public Debt {
        if (owedAmount < 0) {
            throw new IllegalArgumentException("Owed amount cannot be negative");
        }
        if (paidAmount < 0) {
            throw new IllegalArgumentException("Paid amount cannot be negative");
        }
        if (owedAmount == 0 && paidAmount == 0) {
            throw new IllegalArgumentException("Either owed amount or paid amount must be greater than zero");
        }
        if (fromUserId == null) {
            throw new IllegalArgumentException("From User ID cannot be null");
        }
        if (toUserId == null) {
            throw new IllegalArgumentException("To User ID cannot be null");
        }
        if (expenseId == null) {
            throw new IllegalArgumentException("Expense ID cannot be null");
        }
        if (groupId == null) {
            throw new IllegalArgumentException("Group ID cannot be null");
        }
    }
}*/