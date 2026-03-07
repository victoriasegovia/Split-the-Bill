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

}
