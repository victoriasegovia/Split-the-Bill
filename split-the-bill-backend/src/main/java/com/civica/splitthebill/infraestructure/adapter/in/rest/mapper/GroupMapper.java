package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Objects;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;

public final class GroupMapper {

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
            mapMemberIdsToProxies(group.memberIds()),
            mapExpenseIdsToProxies(group.expenseIds())
        );
    }

    private static Set<UserEntity> mapMemberIdsToProxies(Set<Long> ids) {
        return Optional.ofNullable(ids)
                .orElse(Collections.emptySet())
                .stream()
                .map(GroupMapper::createUserProxy)
                .collect(Collectors.toSet());
    }

    private static Set<ExpenseEntity> mapExpenseIdsToProxies(Set<Long> ids) {
        return Optional.ofNullable(ids)
                .orElse(Collections.emptySet())
                .stream()
                .map(GroupMapper::createExpenseProxy)
                .collect(Collectors.toSet());
    }

    private static UserEntity createUserProxy(Long id) {
        UserEntity user = new UserEntity();
        user.setId(id);
        return user;
    }

    private static ExpenseEntity createExpenseProxy(Long id) {
        ExpenseEntity expense = new ExpenseEntity();
        expense.setId(id);
        return expense;
    }
}