package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

public record ExpenseResponse(
        Long expenseId,
        String title,
        Long payerId,
        Long groupId,
        double totalAmount) {}
