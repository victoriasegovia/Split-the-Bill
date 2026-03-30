package com.civica.splitthebill.application.port.in;

import java.util.List;

import com.civica.splitthebill.application.dto.ExpenseDTO;

public interface ExpensePortIn {
    
    ExpenseDTO createExpenseUseCase(ExpenseDTO expenseDTO);
    List<ExpenseDTO> listExpensesInGroupUseCase(Long groupId);
    void addExpenseToGroupUseCase(Long expenseId, Long groupId);

}
