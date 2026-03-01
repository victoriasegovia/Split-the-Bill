package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;

public final class GroupDomainMapper {
    
    private GroupDomainMapper() {}

    public static Group toDomain(GroupEntity e) {
        if (e == null) return null;
        return new Group(e.getId(), e.getName());
    }

    public static GroupEntity toEntity(Group g) {
        if (g == null) return null;
        return new GroupEntity(g.id(), g.name());
    }
}
