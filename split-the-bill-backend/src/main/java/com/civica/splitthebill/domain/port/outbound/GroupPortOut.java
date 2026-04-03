package com.civica.splitthebill.domain.port.outbound;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface GroupPortOut {

    Group save(Group group);
    Optional<Group> findByName(String name);
    Optional<Group> findById(Long id);
    List<Group> findAll();
    List<User> findUsersByGroupId(Long groupId);
}
