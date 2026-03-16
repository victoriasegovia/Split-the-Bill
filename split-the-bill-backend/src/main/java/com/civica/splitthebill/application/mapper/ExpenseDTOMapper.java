package com.civica.splitthebill.application.mapper;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.domain.model.Expense;

public class ExpenseDTOMapper {
    
    private ExpenseDTOMapper() {
    }

    public static ExpenseDTO domainToDTO(Expense expense) {
        return new ExpenseDTO(
                expense.id(),
                expense.title(),
                expense.payerId(),
                expense.groupId(),
                expense.totalAmount());
    }

    public static Expense dtoToDomain(ExpenseDTO expenseDTO) {
        return new Expense(
                expenseDTO.id(),
                expenseDTO.title(),
                expenseDTO.payerId(),
                expenseDTO.groupId(),
                expenseDTO.totalAmount());
    }
    
}
