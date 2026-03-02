package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO used when creating or updating a group through the REST API.
 * Contains only the fields that clients are allowed to provide.
 */
public record GroupRequest(
        @NotBlank
        @Size(min = 1, max = 100)
        String name
) {
}
