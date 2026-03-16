package com.civica.splitthebill.application.services;

import java.util.List;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.port.in.ExpenseService;
import com.civica.splitthebill.domain.port.out.ExpenseRespository;
import com.civica.splitthebill.domain.port.out.GroupRepository;
import com.civica.splitthebill.infraestructure.adapter.in.rest.mapper.ExpenseMapper;

public class ExpenseUseCase implements ExpenseService {

    private final ExpenseRespository expenseRespository;
    private final GroupRepository groupRepository;

    public ExpenseUseCase(ExpenseRespository expenseRespository, GroupRepository groupRepository) {
        this.expenseRespository = expenseRespository;
        this.groupRepository = groupRepository;
    }

    @Override
    public ExpenseDTO createExpenseInGroupUseCase(Long groupId, ExpenseDTO expenseDTO) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Failed to find group with id " + groupId));

        Expense expense = ExpenseMapper.dtoToDomain(expenseDTO);
        Expense savedExpense = expenseRepository.save(expense);
        Group updatedGroup = group.addExpense(savedExpense.id());

        groupRepository.save(updatedGroup);

        return ExpenseMapper.domainToDTO(expense);
    }

    @Override
    public List<ExpenseDTO> listExpensesInGroupUseCase(Long groupId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listExpensesInGroupUseCase'");
    }

}
