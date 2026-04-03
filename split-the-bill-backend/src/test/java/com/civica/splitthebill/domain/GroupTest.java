package com.civica.splitthebill.domain;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import com.civica.splitthebill.domain.model.Group;

class GroupTest {

    @Test
    void groupShouldHaveGivenName() {

        String expectedName = "Mocked Group";

        Group result = new Group(null, expectedName, Set.of(), Set.of());

        assertAll(
                () -> assertEquals(expectedName, result.name(), "Name should match the mock configuration"),
                () -> assertNull(result.groupId(), "ID should be null"),
                () -> assertNotNull(result.memberIds(), "Member IDs should not be null"),
                () -> assertNotNull(result.expenseIds(), "Expense IDs should not be null"));
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {

        String invalidName = null;

        assertThrows(NullPointerException.class, () -> {
            new Group(null, invalidName, Set.of(), Set.of());
        });
    }

    @Test
    void groupShouldHaveSetWithTwoMemberIds() {

        Set<Long> memberIds = new HashSet<>();
        memberIds.add(1L);
        memberIds.add(2L);

        Group result = new Group(null, "", memberIds, Set.of());

        assertAll(
                () -> assertEquals("", result.name(), "Name should match the reference"),
                () -> assertNull(result.groupId(), "ID should be null as configured"),
                () -> assertEquals(2, result.memberIds().size(), "Should have 2 member IDs"),
                () -> assertNotNull(result.expenseIds(), "Expense IDs should not be null"));
    }

    @Test
    void groupShouldHaveSetWithTwoExpenseIds() {

        Set<Long> expenseIds = new HashSet<>();
        expenseIds.add(1L);
        expenseIds.add(2L);

        Group result = new Group(null, "", Set.of(), expenseIds);

        assertAll(
                () -> assertEquals("", result.name(), "Name should match the reference"),
                () -> assertNull(result.groupId(), "ID should be null as configured"),
                () -> assertNotNull(result.memberIds(), "Member IDs should not be null"),
                () -> assertEquals(2, result.expenseIds().size(), "Should have 2 member IDs"));
    }
}
