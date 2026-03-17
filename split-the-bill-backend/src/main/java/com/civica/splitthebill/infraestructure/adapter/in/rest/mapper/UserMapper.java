package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;

import com.civica.splitthebill.domain.model.User;

public final class UserMapper {
    
    private UserMapper() {}

    public static UserEntity domainToEntity(User user) {
        return new UserEntity(
                user.id(),
                user.name(),
                MapperUtils.idsToEntityProxySet(user.groupIds(), GroupEntity::new),
                MapperUtils.idsToEntityProxySet(user.expenseIds(), ExpenseEntity::new));
    }

    public static User entityToDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                MapperUtils.entitiesToIdSet(entity.getGroups(), GroupEntity::getId),
                MapperUtils.entitiesToIdSet(entity.getExpenses(), ExpenseEntity::getId));
    }
}
