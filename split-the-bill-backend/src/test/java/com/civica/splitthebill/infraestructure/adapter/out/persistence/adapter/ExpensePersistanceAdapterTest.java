package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaExpenseRepository;

class ExpensePersistanceAdapterTest {

    private JpaExpenseRepository jpaExpenseRepository;
    private ExpensePersistanceAdapter adapter;

    @BeforeEach
    void setUp() {
        jpaExpenseRepository = Mockito.mock(JpaExpenseRepository.class);
        adapter = new ExpensePersistanceAdapter(jpaExpenseRepository);
    }

    @Test
    void save_ShouldSaveAndReturnExpense() {

        UserEntity payerEntity = UserEntity.createProxy(1L);
        GroupEntity groupEntity = GroupEntity.createProxy(1L);

        Expense domainExpense = new Expense(1L, "Cena", 1L, 1L, 50);
        ExpenseEntity entity = new ExpenseEntity(1L, "Cena", payerEntity, groupEntity, 50.0);

        when(jpaExpenseRepository.save(any(ExpenseEntity.class))).thenReturn(entity);

        Expense result = adapter.save(domainExpense);

        assertAll(
                () -> assertEquals("Cena", result.title()),
                () -> assertEquals(50.0, result.totalAmount()),
                () -> verify(jpaExpenseRepository).save(any(ExpenseEntity.class)));
    }

    @Test
    void findAllByGroupId_ShouldReturnList() {

        UserEntity payerEntity = UserEntity.createProxy(1L);
        GroupEntity groupEntity = GroupEntity.createProxy(1L);

        ExpenseEntity userEntity = new ExpenseEntity(1L, "Gasto 1", payerEntity, groupEntity, 10.0);
        when(jpaExpenseRepository.findAllByGroupId(10L)).thenReturn(List.of(userEntity));

        List<Expense> result = adapter.findAllByGroupId(10L);

        assertEquals(1, result.size());
        assertEquals("Gasto 1", result.get(0).title());
    }
}