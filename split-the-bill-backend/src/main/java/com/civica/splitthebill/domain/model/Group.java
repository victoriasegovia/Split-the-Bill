package com.civica.splitthebill.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.*;

public record Group (
    
    Long id,
    @NotBlank @Size(min = 1, max = 100) String name

    // List<User> users = new ArrayList<>();
    // List<Expense> expenses = new ArrayList<>();
    
) {}
