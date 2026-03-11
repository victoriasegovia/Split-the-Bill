package com.civica.splitthebill.application.services;

import java.util.List;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.domain.port.in.ExpenseService;

public class ExpenseUseCase implements ExpenseService {

    @Override
    public ExpenseDTO createExpenseInGroupUseCase(Long groupId, ExpenseDTO expenseDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createExpenseInGroupUseCase'");
    }

    @Override
    public List<ExpenseDTO> listExpensesInGroupUseCase(Long groupId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listExpensesInGroupUseCase'");
    }
    
}
