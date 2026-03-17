package com.civica.splitthebill.domain.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.LongPredicate;
import java.util.function.Supplier;

import com.civica.splitthebill.domain.exception.EntityAlreadyAssignedException;

public record Group(
    Long id,
    String name,
    Set<Long> memberIds,
    Set<Long> expenseIds) {

  public Group {
    Objects.requireNonNull(name, "Group name cannot be null");
    memberIds = Set.copyOf(Objects.requireNonNullElse(memberIds, Set.of()));
    expenseIds = Set.copyOf(Objects.requireNonNullElse(expenseIds, Set.of()));
  }

  public Group (String name) {this(null, name, Set.of(), Set.of()); }

  public Group addMember(Long memberId) {
    checkExclusivity(memberId, memberIds::contains,
        () -> new EntityAlreadyAssignedException( memberId, "User", this.id, "Group"));

    Set<Long> updatedMembers = new HashSet<>(this.memberIds);
    updatedMembers.add(memberId);

    return new Group(this.id, this.name, updatedMembers, this.expenseIds);
  }

  public Group addExpense(Long expenseId) {
    checkExclusivity(expenseId, expenseIds::contains,
        () -> new EntityAlreadyAssignedException( expenseId, "Expense", this.id, "Group"));

    Set<Long> updatedExpenses = new HashSet<>(this.expenseIds);
    updatedExpenses.add(expenseId);

    return new Group(this.id, this.name, this.memberIds, updatedExpenses);
  }

  private void checkExclusivity(Long id, LongPredicate condition, Supplier<RuntimeException> exception) {
    Objects.requireNonNull(id, "Id cannot be null");
    if (condition.test(id)) { throw exception.get(); }
  }
  
}
