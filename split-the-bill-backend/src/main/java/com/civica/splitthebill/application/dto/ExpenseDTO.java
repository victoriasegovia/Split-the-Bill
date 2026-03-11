package com.civica.splitthebill.application.dto;

public record ExpenseDTO(

        Long id,
        String title,
        Long payerId,
        Long groupId,
        double totalAmount

) {
}
