package com.civica.splitthebill.domain.port.inbound;

import java.util.List;
import com.civica.splitthebill.application.dto.GroupDTO;

public interface GroupPortIn {

    GroupDTO createGroupUseCase(GroupDTO groupDTO);
    List<GroupDTO> listGroupsUseCase();
    List<String> listGroupMembersUseCase(Long groupId);
    GroupDTO listGroupByIdUseCase(Long groupId);

}