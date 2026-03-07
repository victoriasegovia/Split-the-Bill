package com.civica.splitthebill.domain.model;

public record ExpenseParticipation (

    Long participantId,
    Long expenseId,
    double owedAmount,
    double paidAmount

){
    public ExpenseParticipation {
        if (owedAmount < 0) {
            throw new IllegalArgumentException("Owed amount cannot be negative");
        }
        if (paidAmount < 0) {
            throw new IllegalArgumentException("Paid amount cannot be negative");
        }
        if (owedAmount == 0 && paidAmount == 0) {
            throw new IllegalArgumentException("Either owed amount or paid amount must be greater than zero");
        }
        if (participantId == null) {
            throw new IllegalArgumentException("Participant ID cannot be null");
        }
        if (expenseId == null) {
            throw new IllegalArgumentException("Expense ID cannot be null");
        }
    }
}