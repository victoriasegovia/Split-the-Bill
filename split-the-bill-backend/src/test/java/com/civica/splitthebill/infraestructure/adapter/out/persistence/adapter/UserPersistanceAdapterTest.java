package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaExpenseRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaGroupRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaUserRepository;

class UserPersistanceAdapterTest {

    private JpaUserRepository jpaUserRepository;
    private JpaGroupRepository jpaGroupRepository;
    private JpaExpenseRepository jpaExpenseRepository;
    private UserPersistanceAdapter adapter;

    @BeforeEach
    void setUp() {
        jpaUserRepository = Mockito.mock(JpaUserRepository.class);
        jpaGroupRepository = Mockito.mock(JpaGroupRepository.class);
        jpaExpenseRepository = Mockito.mock(JpaExpenseRepository.class);
        adapter = new UserPersistanceAdapter(jpaUserRepository, jpaGroupRepository, jpaExpenseRepository);
    }

    @Test
    void save_ShouldFindDependenciesAndSaveUser() {

        User domainUser = new User(1L, "Victoria", Set.of(10L), Set.of(100L));
        
        when(jpaGroupRepository.findAllById(any())).thenReturn(List.of());
        when(jpaExpenseRepository.findAllById(any())).thenReturn(List.of());
        
        User result = adapter.save(domainUser);

        assertAll(
            () -> assertEquals("Victoria", result.name()),
            () -> verify(jpaGroupRepository).findAllById(domainUser.groupIds()),
            () -> verify(jpaUserRepository).save(any(UserEntity.class))
        );
    }

    @Test
    void findById_ShouldReturnUserWhenExists() {

        UserEntity entity = new UserEntity(1L, "Victoria", List.of(), List.of());
        when(jpaUserRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<User> result = adapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Victoria", result.get().name());
    }
}