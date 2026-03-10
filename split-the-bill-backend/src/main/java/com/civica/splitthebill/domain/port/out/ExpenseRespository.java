package com.civica.splitthebill.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.civica.splitthebill.domain.model.Expense;

public interface ExpenseRespository {
    Optional<Expense> save(Long groupId, Expense expense);
    List<Expense> findAllInGroup(Long groupId);
}
