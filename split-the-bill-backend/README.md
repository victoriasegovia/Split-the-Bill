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

  public User addGroup(Long groupId) {
        checkExclusivity(groupId, groupIds::contains,
                () -> new EntityAlreadyAssignedException(groupId, GROUP, this.id, USER));

        Set<Long> updatedGroup = new HashSet<>(this.groupIds);
        updatedGroup.add(groupId);

        return new User(this.id, this.name, updatedGroup, this.expenseIds);
    }

    public User addExpense(Long expenseId) {
        checkExclusivity(expenseId, expenseIds::contains,
                () -> new EntityAlreadyAssignedException(expenseId, EXPENSE, this.id, USER));

        Set<Long> updateExpense = new HashSet<>(this.expenseIds);
        updateExpense.add(expenseId);

        return new User(this.id, this.name, this.groupIds, updateExpense);
    }

    private void checkExclusivity(Long id, LongPredicate condition, Supplier<RuntimeException> exception) {
        Optional.of(id)
                .filter(condition::test)
                .ifPresent(val -> {
                    throw exception.get();
                });
    }