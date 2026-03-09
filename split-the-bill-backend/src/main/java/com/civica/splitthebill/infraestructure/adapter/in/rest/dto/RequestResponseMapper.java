package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import org.springframework.stereotype.Component;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class RequestResponseMapper {

    private RequestResponseMapper() {
    }

    public static GroupResponse domainDTOToResponse(GroupDTO groupDTO, List<String> memberNames) {
        return new GroupResponse(
            groupDTO.id(),
            groupDTO.name(),
            memberNames
        );
    }

    public static GroupDTO requestToDomainDTO(GroupRequest groupRequest) {
        return new GroupDTO(
            groupRequest.id(),
            groupRequest.name(),
            groupRequest.membersIds()
        );
    }
}
