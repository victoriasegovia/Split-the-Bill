package com.civica.splitthebill.application.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.application.mapper.ExpenseDTOMapper;
import com.civica.splitthebill.domain.port.inbound.ListExpensesInGroupPortInbound;
import com.civica.splitthebill.domain.port.outbound.ExpensePortOut;

@Service
public class ListExpensesInGroupUseCase implements ListExpensesInGroupPortInbound {

    private final ExpensePortOut expenseRepository;
    private final ExpenseDTOMapper expenseDTOMapper;

    public ListExpensesInGroupUseCase(ExpensePortOut expenseRepository, ExpenseDTOMapper expenseDTOMapper) {
        this.expenseRepository = expenseRepository;
        this.expenseDTOMapper = expenseDTOMapper;
    }

    @Override
    public List<ExpenseDTO> execute(Long groupId) {
        return expenseRepository.findAllByGroupId(groupId)
                .stream()
                .map(expenseDTOMapper::domainToDTO)
                .toList();
    }
}
