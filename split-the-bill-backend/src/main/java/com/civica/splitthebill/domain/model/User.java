package com.civica.splitthebill.domain.model;

import java.util.Objects;
import java.util.Set;

public record User(
        Long userId,
        String name,
        Set<Long> groupIds,
        Set<Long> expenseIds) {

    public User {
        groupIds = Set.copyOf(Objects.requireNonNullElse(groupIds, Set.of()));
        expenseIds = Set.copyOf(Objects.requireNonNullElse(expenseIds, Set.of()));
    }
}
