package com.civica.splitthebill.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.civica.splitthebill.domain.model.Expense;

public interface ExpensePortOut {
    Optional<Expense> save(Expense expense);
    List<Expense> findAllByGroupId(Long groupId);
    Optional<Expense> findById(Long expenseId);
}
