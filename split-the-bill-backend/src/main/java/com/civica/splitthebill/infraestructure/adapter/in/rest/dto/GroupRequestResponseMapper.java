package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import com.civica.splitthebill.application.dto.GroupDTO;

import java.util.List;

public final class GroupRequestResponseMapper {
    
    private GroupRequestResponseMapper() {}


    public static GroupResponse domainDTOToResponse(GroupDTO groupDTO, List<String> memberNames) {
        return new GroupResponse(
            groupDTO.groupId(),
            groupDTO.name(),
            memberNames
        );
    }

    public static GroupDTO requestToDomainDTO(GroupRequest groupRequest) {
        return new GroupDTO(
            groupRequest.id(),
            groupRequest.name()
        );
    }
}
