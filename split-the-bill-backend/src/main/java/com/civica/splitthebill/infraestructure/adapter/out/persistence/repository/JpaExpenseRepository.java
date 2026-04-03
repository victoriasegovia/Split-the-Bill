package com.civica.splitthebill.infraestructure.adapter.out.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;

public interface JpaExpenseRepository extends JpaRepository <ExpenseEntity, Long> {
    List<ExpenseEntity> findAllByGroupId(Long groupId);
}
