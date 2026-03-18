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
            MapperUtils.entitiesToIdSet(entity.getMembers(), UserEntity::getId),
            MapperUtils.entitiesToIdSet(entity.getExpenses(), ExpenseEntity::getId)
        );
    }

    public static GroupEntity domaintoEntity(Group group) {
        return new GroupEntity(
            group.groupId(),
            group.name(),
            MapperUtils.idsToEntityProxySet(group.memberIds(), UserEntity::new),
            MapperUtils.idsToEntityProxySet(group.expenseIds(), ExpenseEntity::new)
        );
    }

    }