package com.civica.splitthebill.domain.model;

import java.util.List;

public record Expense(
        Long id,
        String title,
        Long payerId,
        Long groupId,
        double totalAmount,
        List<ExpenseParticipation> participations
        ) {

    public Expense {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Expense title cannot be null or blank");
        }
        if (payerId == null) {
            throw new IllegalArgumentException("Expense payer cannot be null");
        }
        if (groupId == null) {
            throw new IllegalArgumentException("Expense group cannot be null");
        }
        if (totalAmount <= 0) {
            throw new IllegalArgumentException("Expense total amount must be a positive number");
        }
        if (participations == null || participations.isEmpty()) {
            throw new IllegalArgumentException("Expense must have at least one participation");
        }
    }
}
