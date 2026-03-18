# SPLIT THE BILL 

public Group addMember(Long memberId) {
    checkExclusivity(memberId, memberIds::contains,
        () -> new EntityAlreadyAssignedException(memberId, "User", this.id, "Group"));

    Set<Long> updatedMembers = new HashSet<>(this.memberIds);
    updatedMembers.add(memberId);

    return new Group(this.id, this.name, updatedMembers, this.expenseIds);
  }

  public Group addExpense(Long expenseId) {
    checkExclusivity(expenseId, expenseIds::contains,
        () -> new EntityAlreadyAssignedException(expenseId, "Expense", this.id, "Group"));

    Set<Long> updatedExpenses = new HashSet<>(this.expenseIds);
    updatedExpenses.add(expenseId);

    return new Group(this.id, this.name, this.memberIds, updatedExpenses);
  }

  private void checkExclusivity(Long id, LongPredicate condition, Supplier<RuntimeException> exception) {
    Objects.requireNonNull(id, "Id cannot be null");
    if (condition.test(id)) {throw exception.get();}
  }