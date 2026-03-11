package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;

import java.util.List;
import com.civica.splitthebill.domain.model.User;

public final class UserMapper {

    public UserMapper() {
    }

    public static UserEntity domaintoEntity(User user, List<GroupEntity> groups, List<ExpenseEntity> expenses) {
        return new UserEntity(user.id(), user.name(), groups, expenses);
    }

    public static User entitytoDomain(UserEntity entity) {

        List<Long> groupIds = entity.getGroups()
                .stream()
                .map(GroupEntity::getId)
                .toList();

        List<Long> expenseIds = entity.getExpenses()
                .stream()
                .map(ExpenseEntity::getId)
                .toList();

        User user = new User(
                entity.getId(),
                entity.getName(),
                groupIds,
                expenseIds
        );

        return user;
    }
}
