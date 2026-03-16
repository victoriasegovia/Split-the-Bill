package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import java.util.Set;

public record GroupResponse(

        Long id,
        String name,
        Set<String> memberNames,
        Set<Long> memberIds

) {
}
