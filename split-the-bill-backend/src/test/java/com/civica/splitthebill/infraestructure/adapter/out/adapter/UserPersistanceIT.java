package com.civica.splitthebill.infraestructure.adapter.out.adapter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.civica.splitthebill.AbstractIntegrationTest;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.GroupPortOut;
import com.civica.splitthebill.domain.port.out.UserPortOut;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter.GroupPersistanceAdapter;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter.UserPersistanceAdapter;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper.GroupMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper.UserMapper;

@Testcontainers
@DataJpaTest
@Import({
    UserPersistanceAdapter.class, UserMapper.class, 
    GroupPersistanceAdapter.class, GroupMapper.class
})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class UserPersistanceIT extends AbstractIntegrationTest {

    private final UserPortOut userRepository;
    private final GroupPortOut groupRepository;

    public UserPersistanceIT (UserPortOut userRepository, GroupPortOut groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }
    
    @Test
    void shouldSaveUserAndAssignToGroup() {

        Group group = groupRepository.save(new Group(null, "Viaje", new HashSet<>(), new HashSet<>()));
        
        User user = new User(null, "Victoria", Set.of(group.groupId()), new HashSet<>());
        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.userId());
        assertTrue(savedUser.groupIds().contains(group.groupId()));
    }
}
