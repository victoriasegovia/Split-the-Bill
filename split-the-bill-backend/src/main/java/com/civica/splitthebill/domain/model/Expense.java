package com.civica.splitthebill.domain.model;

import java.util.Objects;
import java.util.function.Supplier;

public record Expense(
        Long id,
        String title,
        Long payerId,
        Long groupId,
        double totalAmount
        ) {

    public Expense {
        Objects.requireNonNull(title, "Group name cannot be null");
        Objects.requireNonNull(payerId, "Payer Id cannot be null");
        Objects.requireNonNull(groupId, "Group Id cannot be null");

        ensurePositive(totalAmount > 0, () -> new IllegalArgumentException("Total amount must be positive"));
    }

    private void ensurePositive (boolean condition, Supplier<RuntimeException> exception) {
        if (!condition) throw exception.get();
    }

}
