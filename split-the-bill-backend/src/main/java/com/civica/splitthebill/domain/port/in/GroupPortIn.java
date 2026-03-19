package com.civica.splitthebill.domain.port.in;

import java.util.Set;

import com.civica.splitthebill.application.dto.GroupDTO;

public interface GroupPortIn {

    GroupDTO createGroupUseCase(GroupDTO groupDTO);
    Set<GroupDTO> listGroupsUseCase();
    Set<String> listGroupMembersUseCase(Long groupId);
    GroupDTO listGroupByIdUseCase(Long groupId);

}