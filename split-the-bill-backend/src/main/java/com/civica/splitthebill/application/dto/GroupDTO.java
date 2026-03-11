package com.civica.splitthebill.application.dto;

import java.util.List;

public record GroupDTO(

    Long id,
    String name,
    List<Long> membersIds,
    List<Long> expenseIds
) {}
