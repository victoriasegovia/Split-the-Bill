package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;

public record ExpenseResponse(
        Long expenseId,
        @NotNull
        String title,
        @NotNull
        double totalAmount) {}
