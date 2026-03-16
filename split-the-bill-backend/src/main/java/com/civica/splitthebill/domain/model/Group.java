package com.civica.splitthebill.domain.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.LongPredicate;
import java.util.function.Supplier;

import com.civica.splitthebill.domain.exception.DuplicateExpenseInGroupException;
import com.civica.splitthebill.domain.exception.DuplicateUserInGroupException;

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

  public Group addMember(Long memberId) {
    checkExclusivity(memberId, memberIds::contains,
        () -> new DuplicateUserInGroupException(memberId, this.id));

    Set<Long> updatedMembers = new HashSet<>(this.memberIds);
    updatedMembers.add(memberId);

    return new Group(this.id, this.name, updatedMembers, this.expenseIds);
  }

  public Group addExpense(Long expenseId) {
    checkExclusivity(expenseId, expenseIds::contains,
        () -> new DuplicateExpenseInGroupException(expenseId, this.id));

    Set<Long> updatedExpenses = new HashSet<>(this.expenseIds);
    updatedExpenses.add(expenseId);

    return new Group(this.id, this.name, this.memberIds, updatedExpenses);
  }

  private void checkExclusivity(Long id, LongPredicate condition, Supplier<RuntimeException> exception) {
    Objects.requireNonNull(id, "Id cannot be null");
    Optional.of(id)
        .filter(condition::test)
        .ifPresent(val -> {
          throw exception.get();
        });
  }
}
