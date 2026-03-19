package com.civica.splitthebill.application.services;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.application.mapper.ExpenseDTOMapper;
import com.civica.splitthebill.domain.exception.EntityAlreadyAssignedException;
import com.civica.splitthebill.domain.exception.EntityNotFoundException;
import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.port.in.ExpensePortIn;
import com.civica.splitthebill.domain.port.out.ExpensePortOut;
import com.civica.splitthebill.domain.port.out.GroupPortOut;

@Service
public class ExpenseUseCase implements ExpensePortIn {

    private static final String EXPENSE_NOT_SAVED = "Expense could not be saved.";
    private static final String ID_NOT_NULL = "Id cannot be null";
    private static final String GROUP = "Group";
    private static final String EXPENSE = "Expense";

    private final ExpensePortOut expenseRepository;
    private final GroupPortOut groupRepository;

    public ExpenseUseCase(ExpensePortOut expenseRepository, GroupPortOut groupRepository) {
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public ExpenseDTO createExpenseUseCase(ExpenseDTO expenseDTO) {

        Expense expense = ExpenseDTOMapper.dtoToDomain(expenseDTO);
        groupRepository.findById(expense.groupId())
                .orElseThrow(() -> new EntityNotFoundException(expense.groupId(), GROUP));

        Expense savedExpense = expenseRepository.save(expense)
                .orElseThrow(() -> new RuntimeException(EXPENSE_NOT_SAVED));

        addExpenseToGroupUseCase(savedExpense.expenseId(), expense.groupId());

        return ExpenseDTOMapper.domainToDTO(expense);
    }

    @Override
    public void addExpenseToGroupUseCase(Long expenseId, Long groupId) {
        Objects.requireNonNull(expenseId, ID_NOT_NULL);
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException(groupId, GROUP));
        UseCaseUtils.checkExclusivity(expenseId, group.expenseIds()::contains, (() -> new EntityAlreadyAssignedException(expenseId, EXPENSE, groupId, GROUP)));

        Set<Long> newExpenseIds = new HashSet<>(group.expenseIds());
        newExpenseIds.add(expenseId);
        
        Group updated = new Group(group.groupId(), group.name(), group.memberIds(), newExpenseIds);
        groupRepository.save(updated);
    }

    @Override
    public Set<ExpenseDTO> listExpensesInGroupUseCase(Long groupId) {
        return groupRepository.findById(groupId)
                .stream()
                .flatMap(group -> group.expenseIds().stream())
                .map(expenseRepository::findById)
                .flatMap(Optional::stream)
                .map(ExpenseDTOMapper::domainToDTO)
                .collect(Collectors.toSet());
    }

}
