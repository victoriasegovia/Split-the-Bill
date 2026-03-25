package com.civica.splitthebill.infraestructure.adapter.out.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.civica.splitthebill.AbstractIntegrationTest;
import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.ExpensePortOut;
import com.civica.splitthebill.domain.port.out.GroupPortOut;
import com.civica.splitthebill.domain.port.out.UserPortOut;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter.ExpensePersistanceAdapter;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter.GroupPersistanceAdapter;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter.UserPersistanceAdapter;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper.ExpenseMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper.GroupMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper.UserMapper;

@Testcontainers
@DataJpaTest
@Import({ 
    ExpensePersistanceAdapter.class, ExpenseMapper.class,
    UserPersistanceAdapter.class, UserMapper.class,
    GroupPersistanceAdapter.class, GroupMapper.class 
})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ExpensePersistanceIT extends AbstractIntegrationTest {

    private final ExpensePortOut expenseRepository;
    private final UserPortOut userRepository;
    private final GroupPortOut groupRepository;

    public ExpensePersistanceIT(ExpensePortOut expenseRepository, UserPortOut userRepository, GroupPortOut groupRepository){
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Test
    void shouldPersistExpense() {

        Group group = groupRepository.save(new Group(null, "Cena", new HashSet<>(), new HashSet<>()));
        User user = userRepository.save(new User(null, "Juan", Set.of(group.groupId()), new HashSet<>()));
        
        Expense expense = new Expense(
            null, 
            "Pizza nocturna", 
            user.userId(), 
            group.groupId(), 
            15.50);

        Expense saved = expenseRepository.save(expense);

        assertNotNull(saved.expenseId());
        assertEquals(15.50, saved.totalAmount());
        assertEquals(saved.payerId(), user.userId());
        
        Optional<Expense> retrieved = expenseRepository.findById(saved.expenseId());
        assertTrue(retrieved.isPresent());
    }
}
