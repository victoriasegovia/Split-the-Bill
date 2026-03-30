package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;

public record UserRequest(
    Long userId,
    @NotNull
    String name
) {}
