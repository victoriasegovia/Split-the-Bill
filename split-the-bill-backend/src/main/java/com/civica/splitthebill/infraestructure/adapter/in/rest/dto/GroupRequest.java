package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record GroupRequest(
        Long id,
        @NotBlank
        String name
){}
