package com.civica.splitthebill.application.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.application.mapper.ExpenseDTOMapper;
import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.port.in.ExpensePortIn;
import com.civica.splitthebill.domain.port.out.ExpensePortOut;
import com.civica.splitthebill.domain.port.out.GroupPortOut;

public class ExpenseUseCase implements ExpensePortIn {

    private static final String GROUP_NOT_FOUND = "Failed to find group.";
    private static final String EXPENSE_NOT_SAVED = "Expense could not be saved.";

    private final ExpensePortOut expenseRepository;
    private final GroupPortOut groupRepository;

    public ExpenseUseCase(ExpensePortOut expenseRepository, GroupPortOut groupRepository) {
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public ExpenseDTO createExpenseInGroupUseCase(ExpenseDTO expenseDTO) {

        Expense expense = ExpenseDTOMapper.dtoToDomain(expenseDTO);
        Group group = groupRepository.findById(expense.groupId())
                .orElseThrow(() -> new RuntimeException(GROUP_NOT_FOUND));

        Expense savedExpense = expenseRepository.save(expense)
                .orElseThrow(() -> new RuntimeException(EXPENSE_NOT_SAVED));

        Set<Long> newExpenseIds = group.expenseIds();
        newExpenseIds.add(savedExpense.expenseId());

        Group updated = new Group(group.groupId(), group.name(), group.memberIds(), newExpenseIds);
        groupRepository.save(updated);

        return ExpenseDTOMapper.domainToDTO(expense);
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
