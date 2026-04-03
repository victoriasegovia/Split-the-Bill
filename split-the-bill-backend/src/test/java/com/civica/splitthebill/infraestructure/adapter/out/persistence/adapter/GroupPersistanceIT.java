package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.civica.splitthebill.AbstractIntegrationTest;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.port.outbound.GroupPortOut;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper.GroupMapper;

@Testcontainers
@DataJpaTest
@Import({ GroupPersistanceAdapter.class, GroupMapper.class })
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class GroupPersistanceIT extends AbstractIntegrationTest {

    private GroupPortOut groupRepository;

    public GroupPersistanceIT(GroupPortOut groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Test
    void shouldSaveAndLoadGroup() {

        Group group = new Group(null, "Viaje a Granada", new HashSet<>(), new HashSet<>());
        Group saved = groupRepository.save(group);

        assertNotNull(saved.groupId());
        assertEquals("Viaje a Granada", saved.name());
    }
}