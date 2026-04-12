package com.civica.splitthebill.domain.port.inbound;

import com.civica.splitthebill.application.dto.ExpenseDTO;

public interface CreateExpensePortInbound {
    ExpenseDTO execute(ExpenseDTO expenseDTO);
}
