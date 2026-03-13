package com.civica.splitthebill.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.LongPredicate;
import java.util.function.Supplier;

import com.civica.splitthebill.domain.exception.DuplicateExpenseInGroupException;
import com.civica.splitthebill.domain.exception.DuplicateUserInGroupException;

public record Group(
    Long id,
    String name,
    List<Long> memberIds,
    List<Long> expenseIds) {

  public Group {
    Objects.requireNonNull(name, "Group name cannot be null");
  }

  public Group addMember(Long memberId) {
    
    validateState(memberId, memberIds::contains, 
        () -> new DuplicateUserInGroupException(memberId, this.id));

    List<Long> updatedMembers = new ArrayList<>(this.memberIds);
    updatedMembers.add(memberId);

    return new Group(this.id, this.name, updatedMembers, this.expenseIds);
  }

  public Group addExpense(Long expenseId) {
    validateState(expenseId, expenseIds::contains,
        () -> new DuplicateExpenseInGroupException(expenseId, this.id));

    List<Long> updatedExpenses = new ArrayList<>(this.expenseIds);
    updatedExpenses.add(expenseId);

    return new Group(this.id, this.name, this.memberIds, updatedExpenses);
  }

  private void validateState(Long id, LongPredicate condition, Supplier<RuntimeException> exception) {
    Objects.requireNonNull(id, "Id cannot be null");
    if (condition.test(id)) throw exception.get();
  }
}
