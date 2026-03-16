package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;

import java.util.Set;
import java.util.stream.Collectors;

import com.civica.splitthebill.domain.model.User;

public final class UserMapper {

    public UserMapper() {
    }

    public static UserEntity domaintoEntity(User user, Set<GroupEntity> groups, Set<ExpenseEntity> expenses) {
        return new UserEntity(user.id(), user.name(), groups, expenses);
    }

    public static User entitytoDomain(UserEntity entity) {

        Set<Long> groupIds = entity.getGroups()
                .stream()
                .map(GroupEntity::getId)
                .collect(Collectors.toSet());

        Set<Long> expenseIds = entity.getExpenses()
                .stream()
                .map(ExpenseEntity::getId)
                .collect(Collectors.toSet());

        return new User(
                entity.getId(),
                entity.getName(),
                groupIds,
                expenseIds);
    }
}
