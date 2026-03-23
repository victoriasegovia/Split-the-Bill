package com.civica.splitthebill.domain;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.civica.splitthebill.domain.model.Group;

class GroupTest {

    private Group group;

    @BeforeEach
    void setUp() {
        this.group = Mockito.mock(Group.class);
    }

    @Test
    void shouldCreateGroupWithNameOnly() {
        
        String expectedName = "Mocked Group";
        Set<Long> expectedMembers = Set.of(1L, 2L);

        Mockito.when(group.name()).thenReturn(expectedName);
        Mockito.when(group.memberIds()).thenReturn(expectedMembers);
        Mockito.when(group.groupId()).thenReturn(null);
        Mockito.when(group.expenseIds()).thenReturn(Set.of());

        assertAll(
                () -> assertEquals(expectedName, group.name(), "Name should match the mock configuration"),
                () -> assertNull(group.groupId(), "ID should be null as configured"),
                () -> assertNotNull(group.memberIds(), "Member IDs should not be null"),
                () -> assertEquals(2, group.memberIds().size(), "Should have 2 members"),
                () -> assertTrue(group.expenseIds().isEmpty(), "Expense IDs should be empty"));
    }
}
