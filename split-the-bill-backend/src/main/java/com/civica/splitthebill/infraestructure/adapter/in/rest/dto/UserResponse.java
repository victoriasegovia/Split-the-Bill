package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import java.util.List;
import com.civica.splitthebill.application.dto.ExpenseDTO;

public record UserResponse(
                Long userId,
                String name,
                List<ExpenseDTO> expenses) {}
