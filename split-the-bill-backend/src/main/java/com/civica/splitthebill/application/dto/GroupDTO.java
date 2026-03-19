package com.civica.splitthebill.application.dto;

import java.util.Set;

public record GroupDTO(
    Long groupId,
    String name,
    Set<Long> membersIds,
    Set<Long> expenseIds
) {}
