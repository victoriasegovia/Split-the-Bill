package com.civica.splitthebill.domain.model;

public record Group(

    Long id,
    String name

) {

  public Group {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Group name cannot be null or empty.");
    }
    if (name.length() > 50 || name.length() < 3) {
      throw new IllegalArgumentException("Group name must be between 3 and 50 characters.");
    }
  }
}
