package com.civica.splitthebill.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.civica.splitthebill.domain.exception.DuplicateExpenseInGroupException;
import com.civica.splitthebill.domain.exception.DuplicateUserInGroupException;

public record Group(
        Long id,
        String name,
        List<Long> memberIds,
        List<Long> expenseIds
        ) {

    public Group    {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Group name cannot be null or empty.");
        }
        if (name.length() > 50 || name.length() < 3) {
            throw new IllegalArgumentException("Group name must be between 3 and 50 characters.");
        }
    }

    public Group addMember(Long memberId) {
        if (memberId == null || name.isBlank()) {
            throw new IllegalArgumentException("Member id cannot be null or empty.");
        }
        if (this.memberIds.contains(memberId)) {
            throw new DuplicateUserInGroupException(memberId, this.id);
        }

        List<Long> updatedMembers = new ArrayList<>(this.memberIds);
        updatedMembers.add(memberId);

        return new Group(this.id, this.name, updatedMembers, this.expenseIds);
    }

    public Group addExpense(Long expenseId) {
      if (expenseId == null || name.isBlank()) {
            throw new IllegalArgumentException("Expense id cannot be null or empty.");
        }
        if (this.expenseIds.contains(expenseId)) {
            throw new DuplicateExpenseInGroupException(expenseId, this.id);
        }

        List<Long> updatedExpenses = new ArrayList<>(this.expenseIds);
        updatedExpenses.add(expenseId);

        return new Group(this.id, this.name, this.memberIds, updatedExpenses);
    }
}
