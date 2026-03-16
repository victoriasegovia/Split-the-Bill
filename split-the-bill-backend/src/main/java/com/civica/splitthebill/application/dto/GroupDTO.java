package com.civica.splitthebill.application.dto;

import java.util.Set;

public record GroupDTO(

    Long id,
    String name,
    Set<Long> membersIds,
    Set<Long> expenseIds
) {}
