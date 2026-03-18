package com.civica.splitthebill.domain.model;

import java.util.Objects;
import java.util.Set;

public record Group(
    Long groupId,
    String name,
    Set<Long> memberIds,
    Set<Long> expenseIds) {

  private static final String GROUP_NULL_MESSAGE = "Group name cannot be null";

  public Group {
    Objects.requireNonNull(name, GROUP_NULL_MESSAGE);
    memberIds = Set.copyOf(Objects.requireNonNullElse(memberIds, Set.of()));
    expenseIds = Set.copyOf(Objects.requireNonNullElse(expenseIds, Set.of()));
  }

}
