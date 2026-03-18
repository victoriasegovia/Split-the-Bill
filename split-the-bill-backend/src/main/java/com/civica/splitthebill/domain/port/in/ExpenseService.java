package com.civica.splitthebill.domain.port.in;

import java.util.Set;

import com.civica.splitthebill.application.dto.ExpenseDTO;

public interface ExpenseService {
    
    ExpenseDTO createExpenseInGroupUseCase(ExpenseDTO expenseDTO);
    Set<ExpenseDTO> listExpensesInGroupUseCase(Long groupId);

}
