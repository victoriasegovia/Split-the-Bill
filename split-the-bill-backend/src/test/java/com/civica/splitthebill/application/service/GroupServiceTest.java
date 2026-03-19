package com.civica.splitthebill.application.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.application.services.GroupUseCases;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.port.out.GroupPortOut;
import com.civica.splitthebill.domain.exception.EntityAlreadyAssignedException;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupPortOut groupRepository;

    @InjectMocks
    private GroupUseCases groupUseCases; // -> next task in jira

    @Test
    void createGroup_HappyPath() {

        GroupDTO inputDto = new GroupDTO(null, "Dinner in Granada", Set.of(), Set.of());

        GroupDTO result = groupUseCases.createGroupUseCase(inputDto);
        assertAll(
                () -> assertNotNull(result.groupId()),
                () -> assertEquals("Dinner in Granada", result.name()),
                () -> verify(groupRepository).save(any(Group.class)));
    }

    @Test
    void createGroup_NameDuplicated() {

        GroupDTO inputDto = new GroupDTO(null, "Repeated", Set.of(), Set.of());
        Group existingGroup = new Group(1L, "Repeated", Set.of(), Set.of());

        when(groupRepository.findByName("Repeated")).thenReturn(Optional.of(existingGroup));
        assertThrows(EntityAlreadyAssignedException.class, () -> {
            groupUseCases.createGroupUseCase(inputDto);
        });
        verify(groupRepository, never()).save(any());
    }

    @Test
    void listGroups_HappyPath() {
        List<Group> domainGroups = List.of(
                new Group(1L, "Grupo A", Set.of(), Set.of()),
                new Group(2L, "Grupo B", Set.of(), Set.of()));
        when(groupRepository.findAll()).thenReturn(domainGroups);

        Set<GroupDTO> result = groupUseCases.listGroupsUseCase();

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> verify(groupRepository).findAll());
    }

}
