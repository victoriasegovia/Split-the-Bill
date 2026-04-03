package com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;

class GroupMapperTest {

    @Test
    void entityToDomain_ShouldHandleNullCollections() {

        GroupEntity mockEntity = Mockito.mock(GroupEntity.class);
        
        Mockito.when(mockEntity.getId()).thenReturn(1L);
        Mockito.when(mockEntity.getName()).thenReturn("Cena");
        Mockito.when(mockEntity.getMembers()).thenReturn(null); 
        Mockito.when(mockEntity.getExpenses()).thenReturn(null);

        Group result = GroupMapper.entityToDomain(mockEntity);

        assertAll(
                () -> assertEquals(1L, result.groupId()),
                () -> assertNotEquals("Viaje Granada", result.name()),
                () -> assertNotNull(result.memberIds(), "El set de miembros no debe ser null"),
                () -> assertTrue(result.memberIds().isEmpty()),
                () -> assertTrue(result.expenseIds().isEmpty()));
    }

    @Test
    void domainToEntity_ShouldMapCorrectlyWithProxies() {

        Group domain = new Group(1L, "Cena", Set.of(10L, 11L), Set.of(100L));

        GroupEntity result = GroupMapper.domainToEntity(domain);

        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("Cena", result.getName()),
                () -> assertEquals(2, result.getMembers().size()),
                () -> assertTrue(result.getMembers().stream().anyMatch(u -> u.getId().equals(10L))),
                () -> assertEquals(1, result.getExpenses().size()));
    }
}
