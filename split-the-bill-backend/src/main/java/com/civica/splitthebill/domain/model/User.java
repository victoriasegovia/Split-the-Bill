package com.civica.splitthebill.domain.model;

import java.util.List;

public record User(
        Long id,
        String name,
        List<Long> groupIds,
        List<Long> expenseIds
        )
{
    public User {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User name cannot be null or blank");
        }
        if (groupIds == null) {
            throw new IllegalArgumentException("User groupIds cannot be null");
        }
    }
}
