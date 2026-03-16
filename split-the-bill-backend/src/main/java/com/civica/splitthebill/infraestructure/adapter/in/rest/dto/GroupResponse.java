package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;

public record GroupResponse(

        Long id,
        @NotBlank
        String name,
        Set<String> memberNames,
        Set<Long> memberIds

) {
}
