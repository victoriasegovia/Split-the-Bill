package com.civica.splitthebill.infraestructure.adapter.out.adapter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter.GroupPersistanceAdapter;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaExpenseRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaGroupRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaUserRepository;

class GroupPersistanceAdapterTest {

    private JpaGroupRepository jpaGroupRepository;
    private JpaUserRepository jpaUserRepository;

    private GroupPersistanceAdapter adapter;

    @BeforeEach
    void setUp() {

        this.jpaGroupRepository = Mockito.mock(JpaGroupRepository.class);
        this.jpaUserRepository = Mockito.mock(JpaUserRepository.class);

        this.adapter = new GroupPersistanceAdapter(
                jpaGroupRepository,
                jpaUserRepository);
    }

    @Test
    void save_ShouldMapAndSaveEntity() {

        Group domainGroup = new Group(null, "Cena", Set.of(1L), Set.of());
        UserEntity userEntity = new UserEntity(1L, "Victoria", List.of(), List.of());

        Mockito.when(jpaUserRepository.findAllById(Mockito.any())).thenReturn(List.of(userEntity));

        GroupEntity savedEntity = new GroupEntity(1L, "Cena", List.of(userEntity), List.of());
        Mockito.when(jpaGroupRepository.save(Mockito.any(GroupEntity.class))).thenReturn(savedEntity);

        Group result = adapter.save(domainGroup);

        assertAll(
                () -> assertEquals(1L, result.groupId()),
                () -> assertEquals("Cena", result.name()),
                () -> Mockito.verify(jpaGroupRepository).save(Mockito.any(GroupEntity.class)),
                () -> Mockito.verify(jpaUserRepository).findAllById(Mockito.any()));
    }

    @Test
    void findAll_ShouldReturnDomainList() {

        GroupEntity groupEntity1 = new GroupEntity(1L, "Viaje", List.of(), List.of());
        GroupEntity groupEntity2 = new GroupEntity(2L, "Cena", List.of(), List.of());

        Mockito.when(jpaGroupRepository.findAll()).thenReturn(List.of(groupEntity1, groupEntity2));

        List<Group> result = adapter.findAll();

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals("Viaje", result.get(0).name()),
                () -> assertEquals("Cena", result.get(1).name()),
                () -> Mockito.verify(jpaGroupRepository).findAll());
    }
}
