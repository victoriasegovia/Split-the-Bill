package com.civica.splitthebill.domain.port.in;

import java.util.List;

import com.civica.splitthebill.application.dto.GroupDTO;

public interface GroupService {

    GroupDTO createGroupUseCase(GroupDTO groupDTO);
    List<GroupDTO> listGroupsUseCase();
    void addUserToGroupUseCase(Long groupId, Long userId);
}