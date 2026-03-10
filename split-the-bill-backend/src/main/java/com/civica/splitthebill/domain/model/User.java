package com.civica.splitthebill.domain.model;

import java.util.List;

public record User(
        Long id,
        String name,
        List<Long> groupIds,
        List<Long> expenseIds,
        List<Long> debtIds
        )
{
    public User {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User name cannot be null or blank");
        }
    }
}
