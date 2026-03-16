package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record GroupRequest(

        Long id,
        
        @NotBlank
        String name,

        Set<Long> membersIds,

        Set<Long> expenseIds
) {
}
