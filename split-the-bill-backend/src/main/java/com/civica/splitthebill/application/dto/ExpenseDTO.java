package com.civica.splitthebill.application.dto;

public record ExpenseDTO(
        Long expenseId,
        String title,
        Long payerId,
        Long groupId,
        double totalAmount
) {}
