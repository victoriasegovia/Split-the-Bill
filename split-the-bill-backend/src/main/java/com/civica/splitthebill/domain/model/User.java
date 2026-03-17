package com.civica.splitthebill.domain.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.LongPredicate;
import java.util.function.Supplier;

import com.civica.splitthebill.domain.exception.EntityAlreadyAssignedException;

public record User(
        Long id,
        String name,
        Set<Long> groupIds,
        Set<Long> expenseIds
        )
{
    public User {
        Objects.requireNonNull(id, "User Id cannot be null.");
        groupIds = Set.copyOf(Objects.requireNonNullElse(groupIds, Set.of()));
        expenseIds = Set.copyOf(Objects.requireNonNullElse(expenseIds, Set.of()));
    }

    public User addGroup (Long groupId) {
        checkExclusivity(groupId, groupIds::contains, () -> new EntityAlreadyAssignedException(groupId, "Group", this.id,  "User"));
        
        Set<Long> updatedGroup = new HashSet<>(this.groupIds);
        updatedGroup.add(groupId);

        return new User(this.id, this.name, updatedGroup, this.expenseIds);
    }

    public User addExpense (Long expenseId) {
        checkExclusivity(expenseId, expenseIds::contains, () -> new EntityAlreadyAssignedException(expenseId, "Expense", this.id,  "User"));
        
        Set<Long> updateExpense = new HashSet<>(this.expenseIds);
        updateExpense.add(expenseId);

        return new User(this.id, this.name, this.groupIds, updateExpense);
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
