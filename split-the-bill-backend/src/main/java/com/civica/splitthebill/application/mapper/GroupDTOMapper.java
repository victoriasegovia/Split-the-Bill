package com.civica.splitthebill.application.mapper;

import org.springframework.stereotype.Component;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.domain.model.Group;

@Component
public class GroupDTOMapper {

    public GroupDTO domainToDTO(Group group) {

        return new GroupDTO(
                group.groupId(),
                group.name()
        );
    }
}
