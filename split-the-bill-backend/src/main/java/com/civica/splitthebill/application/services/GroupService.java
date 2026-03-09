package com.civica.splitthebill.application.services;

import java.util.List;

import com.civica.splitthebill.application.dto.GroupDTO;

public interface GroupService {

    GroupDTO createGroupUseCase(GroupDTO groupDTO);
    List<GroupDTO> listGroupsUseCase();
    void addUserToGroupUseCase(Long groupId, Long userId);
    void removeUserFromGroupUseCase(Long groupId, Long userId);
}