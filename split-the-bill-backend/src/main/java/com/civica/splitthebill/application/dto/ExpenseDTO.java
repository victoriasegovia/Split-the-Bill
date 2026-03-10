package com.civica.splitthebill.application.dto;

import java.util.List;

public record ExpenseDTO(

        Long id,
        String title,
        Long payerId,
        Long groupId,
        double totalAmount

) {
}
