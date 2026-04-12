package com.civica.splitthebill.domain.port.inbound;

import java.util.List;

import com.civica.splitthebill.application.dto.ExpenseDTO;

public interface ListExpensesInGroupPortInbound {
    List<ExpenseDTO> execute(Long groupId);
}
