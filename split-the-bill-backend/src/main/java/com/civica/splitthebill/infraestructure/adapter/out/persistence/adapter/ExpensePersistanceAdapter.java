package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;

import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.domain.port.out.ExpenseRespository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaExpenseRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ExpensePersistanceAdapter implements ExpenseRespository {

    private final JpaExpenseRepository jpaExpenseRepository;

    public ExpensePersistanceAdapter(JpaExpenseRepository jpaExpenseRepository) {
        this.jpaExpenseRepository = jpaExpenseRepository;
    }
    
    @Override
    public Optional<Expense> save(Long groupId, Expense expense) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<Expense> findAllInGroup(Long groupId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllInGroup'");
    }
    
}
