package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import com.civica.splitthebill.application.dto.GroupDTO;
import java.util.Set;

public final class RequestResponseMapper {

    public static GroupResponse domainDTOToResponse(GroupDTO groupDTO, Set<String> memberNames) {
        return new GroupResponse(
            groupDTO.groupId(),
            groupDTO.name(),
            memberNames,
            groupDTO.expenseIds()
        );
    }

    public static GroupDTO requestToDomainDTO(GroupRequest groupRequest) {
        return new GroupDTO(
            groupRequest.id(),
            groupRequest.name(),
            groupRequest.membersIds(),
            groupRequest.expenseIds()
        );
    }
}
