package com.civica.splitthebill.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.application.services.ExpenseUseCase;
import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.ExpensePortOut;
import com.civica.splitthebill.domain.port.out.GroupPortOut;
import com.civica.splitthebill.domain.port.out.UserPortOut;

class ExpenseServiceTest {
    
    private ExpenseUseCase expenseUseCase;
    private ExpensePortOut expenseRepository;
    private GroupPortOut groupRepository;
    private UserPortOut userRepository;

    @BeforeEach
    void setUp() {
        expenseRepository = Mockito.mock(ExpensePortOut.class);
        groupRepository = Mockito.mock(GroupPortOut.class);
        userRepository = Mockito.mock(UserPortOut.class);
        expenseUseCase = new ExpenseUseCase(expenseRepository, groupRepository, userRepository);
    }

    @Test
    void createExpense_ShouldSuccess_WhenGroupAndPayerExist() {

        Long groupId = 1L;
        Long payerId = 10L;
        ExpenseDTO input = new ExpenseDTO(null, "Pizza", payerId, groupId, 30.0);
        
        Group mockGroup = new Group(groupId, "Amigos", Set.of(payerId), new HashSet<>());
        User mockPayer = new User(payerId, "Victoria", Set.of(groupId), Set.of());
        
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(mockGroup));
        when(userRepository.findById(payerId)).thenReturn(Optional.of(mockPayer));
        
        Expense savedExpense = new Expense(500L, "Pizza", payerId, groupId, 30.0);
        when(expenseRepository.save(any(Expense.class))).thenReturn(savedExpense);

        ExpenseDTO result = expenseUseCase.createExpenseUseCase(input);

        assertNotNull(result);
        assertEquals(500L, result.expenseId());
        verify(expenseRepository).save(any(Expense.class));
        verify(groupRepository).save(any(Group.class)); 
    }

}
