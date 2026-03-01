package com.civica.splitthebill.domain.port.in;

import java.util.List;

import com.civica.splitthebill.domain.model.Group;

public interface GroupUseCases {
    void createGroupUseCase(String groupName);
    List<Group> listGroupsUseCase();
}
