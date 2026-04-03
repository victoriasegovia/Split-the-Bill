package com.civica.splitthebill.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.application.services.GroupUseCases;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.GroupPortOut;

import jakarta.persistence.EntityNotFoundException;

class GroupServiceTest {

    private GroupPortOut groupRepository;
    private GroupUseCases groupUseCases;

    @BeforeEach
    void setUp() {
        this.groupRepository = Mockito.mock(GroupPortOut.class);
        this.groupUseCases = new GroupUseCases(groupRepository);
    }

    @Test
    void createGroup_HappyPath() {

        GroupDTO inputDto = new GroupDTO(null, "Dinner in Granada");
        Group savedGroup = new Group(1L, "Dinner in Granada", Set.of(), Set.of());
        
        Mockito.when(groupRepository.save(any(Group.class))).thenReturn(savedGroup);
        GroupDTO result = groupUseCases.createGroupUseCase(inputDto);

        assertAll(
                () -> assertNotNull(result.groupId()),
                () -> assertEquals("Dinner in Granada", result.name()),
                () -> Mockito.verify(groupRepository).save(any(Group.class)));
    }

    @Test
    void listGroupById_ShouldReturnGroup_WhenExists() {

        Long id = 1L;
        Group existingGroup = new Group(id, "Cena", Set.of(), Set.of());

        Mockito.when(groupRepository.findById(id)).thenReturn(Optional.of(existingGroup));

        GroupDTO result = groupUseCases.listGroupByIdUseCase(id);

        assertEquals("Cena", result.name());
        assertEquals(id, result.groupId());
    }

    @Test
    void listGroupById_ShouldThrowException_WhenNotFound() {

        Long id = 99L;
        Mockito.when(groupRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            groupUseCases.listGroupByIdUseCase(id);
        });
    }

    @Test
    void listGroupMembers_ShouldReturnNamesList() {

        Long groupId = 1L;

        List<User> mockUsers = List.of(
                new User(10L, "Alice", Set.of(), Set.of()),
                new User(11L, "Bob", Set.of(), Set.of()));

        Mockito.when(groupRepository.findUsersByGroupId(groupId)).thenReturn(mockUsers);

        List<String> result = groupUseCases.listGroupMembersUseCase(groupId);

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.contains("Alice")),
                () -> assertTrue(result.contains("Bob")),
                () -> Mockito.verify(groupRepository).findUsersByGroupId(groupId));
    }

    @Test
    void listGroupById_ShouldThrowException_WhenIdIsNull() {

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            groupUseCases.listGroupByIdUseCase(null);
        });

        assertEquals("Id cannot be null", exception.getMessage());
        Mockito.verifyNoInteractions(groupRepository);
    }

}
