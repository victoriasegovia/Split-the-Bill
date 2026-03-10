package com.civica.splitthebill.application.dto;

import java.util.List;

public record UserDTO(
    
    Long id,
    String name,
    List<Long> groupIds,
    List<Long> expenseIds,
    List<Long> debtIds

) {}