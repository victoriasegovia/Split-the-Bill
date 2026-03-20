package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import com.civica.splitthebill.application.dto.ExpenseDTO;

public final class ExpenseRequestResponseMapper {
    
    private ExpenseRequestResponseMapper() {}

    public static ExpenseResponse domainDTOToResponse(ExpenseDTO expenseDTO) {
        return new ExpenseResponse(
            expenseDTO.expenseId(),
            expenseDTO.title(),
            expenseDTO.totalAmount()
            
        );
    }

    public static ExpenseDTO requestToDomainDTO(ExpenseRequest expenseRequest) {
        return new ExpenseDTO(
            expenseRequest.expenseId(),
            expenseRequest.title(),
            expenseRequest.payerId(),
            expenseRequest.groupId(),
            expenseRequest.totalAmount()
        );
    }
}
