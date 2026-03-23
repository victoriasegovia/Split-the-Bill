package com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper;

import java.util.HashSet;
import java.util.Optional;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;

public final class GroupMapper {

    private GroupMapper() {
    }

    public static Group entitytoDomain(GroupEntity entity) {
        return new Group(
                entity.getId(),
                entity.getName(),
                Optional.ofNullable(entity.getMembers())
                        .map(members -> MapperUtils.entitiesToIdSet(members, UserEntity::getId))
                        .orElseGet(HashSet::new),
                Optional.ofNullable(entity.getExpenses())
                        .map(expenses -> MapperUtils.entitiesToIdSet(expenses, ExpenseEntity::getId))
                        .orElseGet(HashSet::new)
        );
    }

    public static GroupEntity domaintoEntity(Group group) {
        return new GroupEntity(
                group.groupId(),
                group.name(),
                MapperUtils.idsToEntityProxySet(group.memberIds(), UserEntity::createProxy),
                MapperUtils.idsToEntityProxySet(group.expenseIds(), ExpenseEntity::createProxy)
        );
    }

}
