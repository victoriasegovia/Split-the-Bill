package com.civica.splitthebill.domain.model;

public record User(
        Long id,
        String name
        )
{
    public User {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User name cannot be null or blank");
        }
    }
}
