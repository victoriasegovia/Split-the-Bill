package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;

import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.domain.port.out.ExpensePortOut;
import com.civica.splitthebill.infraestructure.adapter.in.rest.mapper.ExpenseMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaExpenseRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ExpensePersistanceAdapter implements ExpensePortOut {

    private final JpaExpenseRepository jpaExpenseRepository;

    public ExpensePersistanceAdapter(JpaExpenseRepository jpaExpenseRepository) {
        this.jpaExpenseRepository = jpaExpenseRepository;
    }

    @Override
    public List<Expense> findAllByGroupId(Long groupId) {
        List<ExpenseEntity> expenseEntities = jpaExpenseRepository.findAllByGroupId(groupId);

        return expenseEntities.stream()
                .map(ExpenseMapper::entityToDomain)
                .toList();
    }

    @Override
    public Optional<Expense> save(Expense expense) {

        ExpenseEntity expenseEntity = ExpenseMapper.domainToEntity(expense);

        expenseEntity.setId(expense.expenseId());
        expenseEntity.setTitle(expense.title());
        expenseEntity.setTotalAmount(expense.totalAmount());

        ExpenseEntity savedExpense = jpaExpenseRepository.save(expenseEntity);

        return Optional.of(ExpenseMapper.entityToDomain(savedExpense));
    }

    @Override
    public Optional<Expense> findById(Long expenseId) {
        Optional<ExpenseEntity> expense = jpaExpenseRepository.findById(expenseId);
        return expense
                .map(ExpenseMapper::entityToDomain);
    }

}
