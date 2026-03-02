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

    // additional helpers for DTO conversion
    public static com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupResponse
            toResponse(Group g) {
        if (g == null) return null;
        return new com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupResponse(g.id(), g.name());
    }

    public static Group fromRequest(
            com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupRequest r) {
        if (r == null) return null;
        // only contains name; id will be set by persistence
        return new Group(null, r.name());
    }
}
