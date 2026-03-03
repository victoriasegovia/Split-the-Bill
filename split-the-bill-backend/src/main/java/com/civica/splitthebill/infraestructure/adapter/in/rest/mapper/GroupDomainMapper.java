package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupRequest;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupResponse;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;

public final class GroupDomainMapper {
    
    private GroupDomainMapper() {}

    public static Group toDomain(GroupEntity groupEntity) {
        if (groupEntity == null) return null;
        return new Group(groupEntity.getId(), groupEntity.getName());
    }

    public static GroupEntity toEntity(Group group) {
        if (group == null) return null;
        return new GroupEntity(group.id(), group.name());
    }

    public static GroupResponse toResponse(Group group) {
        if (group == null) return null;
        return new GroupResponse(group.id(), group.name());
    }

    public static Group fromRequest(GroupRequest groupDTORequest) {
        if (groupDTORequest == null) return null;
        return new Group(null, groupDTORequest.name());
    }
}
