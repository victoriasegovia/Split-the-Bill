package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record GroupRequest(

        Long id,
        
        @NotBlank
        @Size(min = 3, max = 50)
        String name,

        List<Long> membersIds
) {
}
