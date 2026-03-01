package com.civica.splitthebill.domain.port.out;

import com.civica.splitthebill.domain.model.Group;
import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    Group save(Group group);
    Optional<Group> findById(Long id);
    Optional<Group> findByName(String name);
    List<Group> findAll();
}
