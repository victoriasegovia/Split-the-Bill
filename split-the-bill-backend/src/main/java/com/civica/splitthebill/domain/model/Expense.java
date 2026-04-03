package com.civica.splitthebill.domain.model;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public record Expense(
        Long expenseId,
        String title,
        Long payerId,
        Long groupId,
        double totalAmount) {

    private static final String TITLE_NOT_NULL = "Group name cannot be null";
    private static final String PAYER_NOT_NULL = "Payer Id cannot be null";
    private static final String GROUP_NOT_NULL = "Group Id cannot be null";
    private static final String AMOUNT_NOT_POSITIVE = "Total amount must be positive";

    public Expense {
        Objects.requireNonNull(title, TITLE_NOT_NULL);
        Objects.requireNonNull(payerId, PAYER_NOT_NULL);
        Objects.requireNonNull(groupId, GROUP_NOT_NULL);

        ensurePositive(totalAmount > 0, () -> new IllegalArgumentException(AMOUNT_NOT_POSITIVE));
    }

    private void ensurePositive(boolean condition, Supplier<RuntimeException> exception) {
        Optional.of(condition)
                .filter(Boolean::booleanValue)
                .orElseThrow(exception);
    }

}
