package com.civica.splitthebill.application.mapper;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.domain.model.Group;

public class GroupDTOMapper {

    private GroupDTOMapper() {
    }

    public static Group dtoToDomain(GroupDTO dto) {

        return new Group(
                dto.groupId(),
                dto.name(),
                dto.membersIds(),
                dto.expenseIds()
        );
    }

    public static GroupDTO domainToDTO(Group group) {

        return new GroupDTO(
                group.groupId(),
                group.name(),
                group.memberIds(),
                group.expenseIds()
        );
    }
}
