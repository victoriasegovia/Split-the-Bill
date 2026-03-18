package com.civica.splitthebill.application.services;

import java.util.Objects;
import java.util.Set;
import java.util.function.LongPredicate;
import java.util.function.Supplier;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.application.mapper.ExpenseDTOMapper;
import com.civica.splitthebill.domain.exception.EntityAlreadyAssignedException;
import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.port.in.ExpenseService;
import com.civica.splitthebill.domain.port.out.ExpenseRespository;
import com.civica.splitthebill.domain.port.out.GroupRepository;

public class ExpenseUseCase implements ExpenseService {

    private final ExpenseRespository expenseRepository;
    private final GroupRepository groupRepository;

    public ExpenseUseCase(ExpenseRespository expenseRepository, GroupRepository groupRepository) {
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public ExpenseDTO createExpenseInGroupUseCase(ExpenseDTO expenseDTO) {
        
        Expense expense = ExpenseDTOMapper.dtoToDomain(expenseDTO);
        
        Group group = groupRepository.findById(expense.groupId())
                .orElseThrow(() -> new RuntimeException("Failed to find group with id " + expense.groupId()));

        Expense savedExpense = expenseRepository.save(expense)
                .orElseThrow(() -> new RuntimeException("Expense could not be saved."));

        checkExclusivity(savedExpense.id(), group.expenseIds()::contains, () -> new EntityAlreadyAssignedException(savedExpense.id(), "Expense", group.groupId(), "Group"));
        
        Set<Long> newExpenseIds = group.expenseIds();
        newExpenseIds.add(savedExpense.id());
        
        Group updated = new Group(group.groupId(), group.name(), group.memberIds(), newExpenseIds);
        groupRepository.save(updated);

        return ExpenseDTOMapper.domainToDTO(expense);
    }

    @Override
    public Set<ExpenseDTO> listExpensesInGroupUseCase(Long groupId) {
        throw new UnsupportedOperationException("Unimplemented method 'listExpensesInGroupUseCase'");
    }

    private void checkExclusivity(Long id, LongPredicate condition, Supplier<RuntimeException> exception) {
        Objects.requireNonNull(id, "Id cannot be null");
        if (condition.test(id)) {
            throw exception.get();
        }
    }
}
