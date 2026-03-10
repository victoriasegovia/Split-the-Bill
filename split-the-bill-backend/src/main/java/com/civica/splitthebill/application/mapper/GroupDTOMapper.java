package com.civica.splitthebill.application.mapper;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.domain.model.Group;

public class GroupDTOMapper {

    private GroupDTOMapper() {
    }

    public static Group dtoToDomain(GroupDTO dto) {

        return new Group(
                dto.id(),
                dto.name(),
                dto.membersIds());
    }

    public static GroupDTO domainToDTO(Group group) {

        return new GroupDTO(
                group.id(),
                group.name(),
                group.membersIds());
    }
}
