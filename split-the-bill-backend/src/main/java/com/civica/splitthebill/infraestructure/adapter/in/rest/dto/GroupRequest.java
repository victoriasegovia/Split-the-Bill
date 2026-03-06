package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GroupRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        String name
) {
}
