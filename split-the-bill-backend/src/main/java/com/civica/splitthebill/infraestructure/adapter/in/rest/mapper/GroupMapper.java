package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;

public final class GroupMapper {
    
    private GroupMapper() {}

    public static Group entitytoDomain(GroupEntity entity) {
        return new Group(
            entity.getId(),
            entity.getName(),
            Optional.ofNullable(entity.getMembers())
                .orElse(Collections.emptySet())
                .stream()
                .map(UserEntity::getId)
                .collect(Collectors.toSet()),
            Optional.ofNullable(entity.getExpenses())
                .orElse(Collections.emptySet())
                .stream()
                .map(ExpenseEntity::getId)
                .collect(Collectors.toSet())
        );
    }

    public static GroupEntity domaintoEntity(Group group) {
        return new GroupEntity(
            group.id(),
            group.name(),
            MapperUtils.idsToEntityProxySet(group.memberIds(), UserEntity::new),
            MapperUtils.idsToEntityProxySet(group.expenseIds(), ExpenseEntity::new)
        );
    }

    }