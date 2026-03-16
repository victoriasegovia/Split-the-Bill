package com.civica.splitthebill.domain.port.out;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import java.util.Optional;
import java.util.Set;

public interface GroupRepository {

    Optional<Group> save(Group group);
    Optional<Group> findByName(String name);
    Optional<Group> findById(Long id);
    Set<Group> findAll();
    Set<User> findUsersByGroupId(Long groupId);
}
