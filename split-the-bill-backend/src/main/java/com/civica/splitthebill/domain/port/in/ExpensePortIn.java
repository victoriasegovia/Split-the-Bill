package com.civica.splitthebill.domain.port.in;

import java.util.Set;

import com.civica.splitthebill.application.dto.ExpenseDTO;

public interface ExpensePortIn {
    
    ExpenseDTO createExpenseUseCase(ExpenseDTO expenseDTO);
    Set<ExpenseDTO> listExpensesInGroupUseCase(Long groupId);
    void addExpenseToGroupUseCase(Long expenseId, Long groupId);

}
