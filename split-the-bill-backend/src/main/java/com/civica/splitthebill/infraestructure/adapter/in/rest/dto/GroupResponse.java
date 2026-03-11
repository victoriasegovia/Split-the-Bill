package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import java.util.List;

public record GroupResponse(

        Long id,
        String name,
        List<String> memberNames,
        List<Long> memberIds

) {
}
