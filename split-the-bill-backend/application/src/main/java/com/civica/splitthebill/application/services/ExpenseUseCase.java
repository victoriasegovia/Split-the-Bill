package com.civica.splitthebill.application.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.application.mapper.ExpenseDTOMapper;
import com.civica.splitthebill.application.port.in.ExpensePortIn;
import com.civica.splitthebill.domain.exception.EntityAlreadyAssignedException;
import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.port.out.ExpensePortOut;
import com.civica.splitthebill.domain.port.out.GroupPortOut;
import com.civica.splitthebill.domain.port.out.UserPortOut;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ExpenseUseCase implements ExpensePortIn {

    private static final String GROUP = "Group";
    private static final String EXPENSE = "Expense";

    private final ExpensePortOut expenseRepository;
    private final GroupPortOut groupRepository;
    private final UserPortOut userPortOut;

    public ExpenseUseCase(ExpensePortOut expenseRepository, GroupPortOut groupRepository, UserPortOut userPortOut) {
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
        this.userPortOut = userPortOut;
    }

    @Override
    public ExpenseDTO createExpenseUseCase(ExpenseDTO expenseDTO) {

        groupRepository.findById(expenseDTO.groupId())
                .orElseThrow(EntityNotFoundException::new);
        userPortOut.findById(expenseDTO.payerId())
                .orElseThrow(EntityNotFoundException::new);

        Expense newExpense = new Expense(null, expenseDTO.title(), expenseDTO.payerId(), expenseDTO.groupId(), expenseDTO.totalAmount());
        Expense savedExpense = expenseRepository.save(newExpense);

        addExpenseToGroupUseCase(savedExpense.expenseId(), savedExpense.groupId());

        return ExpenseDTOMapper.domainToDTO(savedExpense);
    }

    @Override
    public void addExpenseToGroupUseCase(Long expenseId, Long groupId) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(EntityNotFoundException::new);

        Set<Long> expenseIds = new HashSet<>(group.expenseIds());
        UseCaseUtils.checkExclusivity(expenseId, expenseIds::contains, (() -> new EntityAlreadyAssignedException(expenseId, EXPENSE, groupId, GROUP)));

        expenseIds.add(expenseId);

        Group updated = new Group(group.groupId(), group.name(), group.memberIds(), expenseIds);
        groupRepository.save(updated);
    }

    @Override
    public List<ExpenseDTO> listExpensesInGroupUseCase(Long groupId) {
        return expenseRepository.findAllByGroupId(groupId)
                .stream()
                .map(ExpenseDTOMapper::domainToDTO)
                .toList();
    }

}
