package com.civica.splitthebill.application.mapper;

import java.util.ArrayList;
import java.util.HashSet;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.domain.model.Group;

public class GroupDTOMapper {

    private GroupDTOMapper() {
    }

    public static GroupDTO domainToDTO(Group group) {

        return new GroupDTO(
                group.groupId(),
                group.name()
        );
    }
}
