package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import org.springframework.stereotype.Component;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class DTOMapper {

    private DTOMapper() {}

    public static GroupResponse domainToResponse(Group group) {
        List<String> members = group.members().stream().map(member -> member.name()).toList();
        GroupResponse groupResponse = new GroupResponse(group.id(), group.name(), members);
        return groupResponse;
    }

    public static Group requestToDomain(GroupRequest groupRequest) {
        List<User> members = new ArrayList<>();
        if (groupRequest.membersIds() != null) {
            members = groupRequest.membersIds().stream()
                    .map(id -> new User(id, "User " + id))
                    .collect(Collectors.toList());
        }

    Group group = new Group(null, groupRequest.name(), members);
        return group;
    }
}
