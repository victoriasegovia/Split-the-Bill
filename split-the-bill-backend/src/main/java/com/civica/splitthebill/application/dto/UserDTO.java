package com.civica.splitthebill.application.dto;

import java.util.Set;

public record UserDTO(
    Long userId,
    String name,
    Set<Long> groupIds,
    Set<Long> expenseIds

) {}