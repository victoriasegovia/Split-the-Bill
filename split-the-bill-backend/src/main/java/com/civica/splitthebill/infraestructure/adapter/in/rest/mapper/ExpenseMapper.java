package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;

public class ExpenseMapper {

    public static ExpenseEntity domainToEntity(Expense expense) {
        return new ExpenseEntity(
            expense.id(),
            expense.title(),
            createUserProxy(expense.payerId()),
            createGroupProxy(expense.groupId()),
            expense.totalAmount()
        );
    }

    public static Expense entityToDomain(ExpenseEntity entity) {
        return new Expense(
            entity.getId(),
            entity.getTitle(),
            entity.getPayer().getId(),
            entity.getGroup().getId(),
            entity.getTotalAmount()
        );
    }

    private static UserEntity createUserProxy(Long id) {
        UserEntity userProxy = new UserEntity();
        userProxy.setId(id);
        return userProxy;
    }

    private static GroupEntity createGroupProxy(Long id) {
        GroupEntity groupProxy = new GroupEntity();
        groupProxy.setId(id);
        return groupProxy;
    }
}