package com.civica.splitthebill.application.services;

import java.util.List;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.application.mapper.ExpenseDTOMapper;
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
    public ExpenseDTO createExpenseInGroupUseCase(Long groupId, ExpenseDTO expenseDTO) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Failed to find group with id " + groupId));

        Expense expense = ExpenseDTOMapper.dtoToDomain(expenseDTO);
        Expense savedExpense = expenseRepository.save(expense)
                .orElseThrow(() -> new RuntimeException("Expense could not be saved."));
        
        Group updatedGroup = group.addExpense(savedExpense.id());

        groupRepository.save(updatedGroup);

        return ExpenseDTOMapper.domainToDTO(expense);
    }

    @Override
    public List<ExpenseDTO> listExpensesInGroupUseCase(Long groupId) {
        throw new UnsupportedOperationException("Unimplemented method 'listExpensesInGroupUseCase'");
    }

}
