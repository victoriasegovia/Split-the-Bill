package com.civica.splitthebill.domain.model;

public record Balance (
    Long fromUser,
    Long toUser,
    double amount
) {}
