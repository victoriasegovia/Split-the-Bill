package com.civica.splitthebill.domain.port.in;

import java.util.List;
import java.util.Set;

import com.civica.splitthebill.application.dto.GroupDTO;

public interface GroupService {

    GroupDTO createGroupUseCase(GroupDTO groupDTO);
    List<GroupDTO> listGroupsUseCase();
    Set<String> listGroupMembersUseCase(Long groupId);
    GroupDTO listGroupByIdUseCase(Long groupId);

}