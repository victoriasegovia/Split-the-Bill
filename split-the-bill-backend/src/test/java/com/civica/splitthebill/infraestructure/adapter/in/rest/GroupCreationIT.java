package com.civica.splitthebill.infraestructure.adapter.in.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.GroupPortOut;
import com.civica.splitthebill.domain.port.out.UserPortOut;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) // <--- LA CLAVE
class ExpenseIntegrationIT {

    private final MockMvc mockMvc;
    private final GroupPortOut groupRepository;
    private final UserPortOut userRepository;

    // Constructor limpio, como en tus Services
    public ExpenseIntegrationIT(MockMvc mockMvc, 
                                GroupPortOut groupRepository, 
                                UserPortOut userRepository) {
        this.mockMvc = mockMvc;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Test
    void shouldRegisterExpenseFullFlow() throws Exception {
        // 1. GIVEN: Crear el mundo real en H2
        Group group = groupRepository.save(new Group(null, "Cena", new HashSet<>(), new HashSet<>()));
        User payer = userRepository.save(new User(null, "Victoria", Set.of(group.groupId()), new HashSet<>()));
        
        String expenseJson = String.format("""
                {
                    "title": "Pizza",
                    "payerId": %d,
                    "groupId": %d,
                    "totalAmount": 50.0
                }
                """, payer.userId(), group.groupId());

        // 2. WHEN & THEN: El flujo completo
        mockMvc.perform(post("/api/groups/" + group.groupId() + "/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expenseJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.expenseId").exists())
                .andExpect(jsonPath("$.totalAmount").value(50.0));
    }
}
