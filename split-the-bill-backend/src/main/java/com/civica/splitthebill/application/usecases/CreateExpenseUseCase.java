package com.civica.splitthebill.application.usecases;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.application.mapper.ExpenseDTOMapper;
import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.domain.port.inbound.CreateExpensePortInbound;
import com.civica.splitthebill.domain.port.outbound.ExpensePortOut;
import com.civica.splitthebill.domain.port.outbound.GroupPortOut;
import com.civica.splitthebill.domain.port.outbound.UserPortOut;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CreateExpenseUseCase implements CreateExpensePortInbound {

    private final ExpensePortOut expenseRepository;
    private final GroupPortOut groupRepository;
    private final UserPortOut userPortOut;
    private final ExpenseDTOMapper expenseDTOMapper;

    public CreateExpenseUseCase(ExpensePortOut expenseRepository, GroupPortOut groupRepository, UserPortOut userPortOut, ExpenseDTOMapper expenseDTOMapper) {
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
        this.userPortOut = userPortOut;
        this.expenseDTOMapper = expenseDTOMapper;
    }

    @Override
    public ExpenseDTO execute(ExpenseDTO expenseDTO) {
        groupRepository.findById(expenseDTO.groupId())
                .orElseThrow(EntityNotFoundException::new);
        userPortOut.findById(expenseDTO.payerId())
                .orElseThrow(EntityNotFoundException::new);

        Expense newExpense = new Expense(null, expenseDTO.title(), expenseDTO.payerId(), expenseDTO.groupId(), expenseDTO.totalAmount());
        Expense savedExpense = expenseRepository.save(newExpense);

        return expenseDTOMapper.domainToDTO(savedExpense);
    }
}