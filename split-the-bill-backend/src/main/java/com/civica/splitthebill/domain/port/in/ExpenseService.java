package com.civica.splitthebill.domain.port.in;

import java.util.List;
import com.civica.splitthebill.application.dto.ExpenseDTO;

public interface ExpenseService {
    
    ExpenseDTO createExpenseInGroupUseCase(Long groupId, ExpenseDTO expenseDTO);
    List<ExpenseDTO> listExpensesInGroupUseCase(Long groupId);

}
