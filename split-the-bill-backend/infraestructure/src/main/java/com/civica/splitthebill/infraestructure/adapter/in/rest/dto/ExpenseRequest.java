package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;

public record ExpenseRequest(
        Long expenseId,
        @NotNull
        String title,
        @NotNull
        Long payerId,
        @NotNull
        Long groupId,
        @NotNull
        Double totalAmount) {}
