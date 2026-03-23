package com.civica.splitthebill.application.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.application.services.UserUseCases;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.GroupPortOut;
import com.civica.splitthebill.domain.port.out.UserPortOut;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserUseCases userUseCases;
    private UserPortOut userRepository;
    private GroupPortOut groupRepository;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserPortOut.class);
        groupRepository = Mockito.mock(GroupPortOut.class);
        userUseCases = new UserUseCases(userRepository, groupRepository);
    }

    @Test
    void createUserUseCase_ShouldSuccess_WhenGroupExists() {
        
        Long groupId = 1L;
        UserDTO inputDto = new UserDTO(null, "Victoria");
        
        Group mockGroup = new Group(groupId, "Viaje", new HashSet<>(), Set.of());
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(mockGroup));
        
        User savedUser = new User(10L, "Victoria", Set.of(groupId), Set.of());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDTO result = userUseCases.createUserUseCase(inputDto, groupId);

        assertNotNull(result);
        assertEquals(10L, result.userId());
        assertEquals("Victoria", result.name());
        
        verify(userRepository).save(any(User.class));
        verify(groupRepository, times(2)).findById(groupId);
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void createUserUseCase_ShouldThrowException_WhenGroupNotFound() {
        
        Long groupId = 99L;
        UserDTO inputDto = new UserDTO(null, "Victoria");
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> 
            userUseCases.createUserUseCase(inputDto, groupId)
        );
        
        verify(userRepository, never()).save(any());
    }
}