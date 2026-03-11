package com.civica.splitthebill.infraestructure.adapter.out.persistence.repository;

import java.util.List;

import com.civica.splitthebill.domain.port.out.ExpenseRespository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;

public interface JpaExpenseRepository extends ExpenseRespository {
    List<ExpenseEntity> findAllByGroupId(Long groupId);
}
