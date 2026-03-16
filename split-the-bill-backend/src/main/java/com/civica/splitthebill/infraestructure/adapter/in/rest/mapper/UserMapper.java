package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.civica.splitthebill.domain.model.User;

public final class UserMapper {

    public static UserEntity domainToEntity(User user) {
        return new UserEntity(
                user.id(),
                user.name(),
                createGroupProxy(user.groupIds()),
                createExpenseProxy(user.expenseIds())
        );
    }

    public static User entityToDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getGroups()
                        .stream()
                        .map(GroupEntity::getId)
                        .collect(Collectors.toSet()),
                entity.getExpenses()
                        .stream()
                        .map(ExpenseEntity::getId)
                        .collect(Collectors.toSet())
        );
    }

    private static Set<GroupEntity> createGroupProxy(Set<Long> id) {
        GroupEntity groupProxy = new GroupEntity();
        groupProxy.setId(id);
        return groupProxy;
    }

    private static Set<ExpenseEntity> createExpenseProxy(Set<Long> id) {
        ExpenseEntity expenseProxy = new ExpenseEntity();
        expenseProxy.setId(id);
        return expenseProxy;
    }
}
