package com.civica.splitthebill.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.junit.jupiter.api.Test;
import com.civica.splitthebill.domain.model.Group;

class GroupTest {

    @Test
    void shouldCreateGroupWithNameOnly() {
        String groupName = "Group 1";
        Group group = new Group(null, groupName, Set.of(), Set.of());

        assertAll(
                () -> assertEquals(groupName, group.name(), "Name should match the provided input"),
                () -> assertNull(group.groupId(), "ID should be null for a new unsaved group"),
                () -> assertNotNull(group.memberIds(), "Member IDs set should not be null"),
                () -> assertTrue(group.memberIds().isEmpty(), "Member IDs set should be empty"),
                () -> assertNotNull(group.expenseIds(), "Expense IDs set should not be null"),
                () -> assertTrue(group.expenseIds().isEmpty(), "Expense IDs set should be empty"));
    }
}
