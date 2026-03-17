package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;

public class ExpenseMapper {
    
    private ExpenseMapper() {}

    public static ExpenseEntity domainToEntity(Expense expense) {
        return new ExpenseEntity(
            expense.id(),
            expense.title(),
            MapperUtils.createEntityProxy(expense.payerId(), UserEntity::new),
            MapperUtils.createEntityProxy(expense.groupId(), GroupEntity::new),
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