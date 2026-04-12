package com.civica.splitthebill.application.mapper;

import org.springframework.stereotype.Component;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.domain.model.Expense;

@Component
public class ExpenseDTOMapper {

    public ExpenseDTO domainToDTO(Expense expense) {
        return new ExpenseDTO(
                expense.expenseId(),
                expense.title(),
                expense.payerId(),
                expense.groupId(),
                expense.totalAmount());
    }
    
}
