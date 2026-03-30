package com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper;

import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;

public class ExpenseMapper {
    
    private ExpenseMapper() {}

    public static ExpenseEntity domainToEntity(Expense expense) {
        return new ExpenseEntity(
            expense.expenseId(),
            expense.title(),
            MapperUtils.createEntityProxy(expense.payerId(), UserEntity::createProxy),
            MapperUtils.createEntityProxy(expense.groupId(), GroupEntity::createProxy),
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

}