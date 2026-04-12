package com.civica.splitthebill.domain.port.inbound;

public interface AddExpenseToGroupPortInbound {
    void execute(Long expenseId, Long groupId);
}
