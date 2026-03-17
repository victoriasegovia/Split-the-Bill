package com.civica.splitthebill.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.civica.splitthebill.domain.exception.EntityAlreadyAssignedException;
import com.civica.splitthebill.domain.model.Group;

class GroupTest {

    @Test
    void shouldCreateGroupWithNameOnly() {
        String groupName = "Group 1";
        Group group = new Group(groupName);

        assertAll(
                () -> assertEquals(groupName, group.name(), "Name should match the provided input"),
                () -> assertNull(group.id(), "ID should be null for a new unsaved group"),
                () -> assertNotNull(group.memberIds(), "Member IDs set should not be null"),
                () -> assertTrue(group.memberIds().isEmpty(), "Member IDs set should be empty"),
                () -> assertNotNull(group.expenseIds(), "Expense IDs set should not be null"),
                () -> assertTrue(group.expenseIds().isEmpty(), "Expense IDs set should be empty"));
    }

    @Test
    void shouldAddMemberSuccessfully() {
        Group initialGroup = new Group("Trip to Rome");
        Long testUser = 1L;

        Group updatedGroup = initialGroup.addMember(testUser);

        assertAll(
                () -> assertNotSame(initialGroup, updatedGroup, "A Record must return a NEW instance"),
                () -> assertTrue(updatedGroup.memberIds().contains(testUser), "The new group should have the member"),
                () -> assertTrue(initialGroup.memberIds().isEmpty(),
                        "The original group should remain immutable/empty"));
    }

    @Test
    void shouldThrowExceptionWhenMemberIsDuplicate() {
        Long memberId = 1L;
        Group group = new Group(10L, "Trip to Rome", Set.of(memberId), Set.of());

        assertThrows(EntityAlreadyAssignedException.class, () -> {
            group.addMember(memberId);
        });
    }

    @Test
    void shouldAddExpenseSuccessfully() {
        Group initialGroup = new Group("Trip to Rome");
        Long expenseId = 500L;

        Group updatedGroup = initialGroup.addExpense(expenseId);

        assertAll(
                () -> assertTrue(updatedGroup.expenseIds().contains(expenseId)),
                () -> assertNotSame(initialGroup, updatedGroup));
    }

    @Test
    void shouldThrowExceptionWhenExpenseIsDuplicate() {
        Long expenseId = 500L;
        Group group = new Group(10L, "Trip to Rome", Set.of(), Set.of(expenseId));

        assertThrows(EntityAlreadyAssignedException.class, () -> {
            group.addExpense(expenseId);
        });
    }
}
