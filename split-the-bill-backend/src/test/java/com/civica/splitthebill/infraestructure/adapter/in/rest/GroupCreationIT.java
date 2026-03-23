package com.civica.splitthebill.infraestructure.adapter.in.rest;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.GroupPortOut;
import com.civica.splitthebill.domain.port.out.UserPortOut;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.test.context.TestConstructor;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ExpenseIntegrationIT {

    private final MockMvc mockMvc;
    private final GroupPortOut groupRepository;
    private final UserPortOut userRepository;

    public ExpenseIntegrationIT(WebApplicationContext wac,
            GroupPortOut groupRepository,
            UserPortOut userRepository) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Test
    void shouldCreateGroupCorrectly() {
        Group group = new Group(null, "Viaje a París", new HashSet<>(), new HashSet<>());
        Group saved = groupRepository.save(group);

        assertNotNull(saved.groupId());
        assertEquals("Viaje a París", saved.name());
    }

    @Test
    void shouldCreateUserAndAssignToGroup() {
        Group group = groupRepository.save(new Group(null, "Cena", new HashSet<>(), new HashSet<>()));

        User user = new User(null, "Victoria", Set.of(group.groupId()), new HashSet<>());
        User saved = userRepository.save(user);

        assertNotNull(saved.userId());
        assertTrue(saved.groupIds().contains(group.groupId()));
    }

    @Test
    void shouldRegisterExpense_FullFlow() throws Exception {

        Group group = groupRepository.save(new Group(null, "Cena", new HashSet<>(), new HashSet<>()));
        
        Set<Long> userGroups = new HashSet<>();
        userGroups.add(group.groupId());
        
        User payer = userRepository.save(new User(null, "Victoria", userGroups, new HashSet<>()));

        String expenseRequest = String.format("""
                {
                    "title": "Pizza",
                    "payerId": %d,
                    "groupId": %d,
                    "totalAmount": 50.0
                }
                """, payer.userId(), group.groupId());

        mockMvc.perform(post("/api/groups/" + group.groupId() + "/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expenseRequest))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.expenseId").exists())
                .andExpect(jsonPath("$.totalAmount").value(50.0));
    }
}
