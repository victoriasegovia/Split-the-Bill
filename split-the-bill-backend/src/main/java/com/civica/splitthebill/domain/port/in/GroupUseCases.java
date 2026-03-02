package com.civica.splitthebill.domain.port.in;

import java.util.List;

import com.civica.splitthebill.domain.model.Group;

public interface GroupUseCases {
    /**
     * Creates a new group with the given name.
     *
     * @param groupName the name of the new group
     * @return the created domain object, including the generated id
     */
    Group createGroupUseCase(String groupName);

    List<Group> listGroupsUseCase();
}
