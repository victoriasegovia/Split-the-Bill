package com.civica.splitthebill.application.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;

public class GroupDTOMapper {

    private GroupDTOMapper() {
    }

    public static Group dtoToDomain(GroupDTO dto, List<User> members) {

        return new Group(
                dto.id(),
                dto.name(),
                members);
    }

    public static GroupDTO domainToDTO(Group group) {

        List<Long> memberIds = group.members()
                .stream()
                .map(User::id)
                .toList();

        return new GroupDTO(
                group.id(),
                group.name(),
                memberIds);
    }
}
