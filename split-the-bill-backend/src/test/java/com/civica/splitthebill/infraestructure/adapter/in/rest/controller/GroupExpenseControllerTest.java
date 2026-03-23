package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.domain.port.in.ExpensePortIn;
import com.civica.splitthebill.infraestructure.adapter.in.rest.handler.GlobalExceptionHandler;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

class GroupExpenseControllerTest {

    private MockMvc mockMvc;
    private ExpensePortIn expenseService;

    @BeforeEach
    void setUp() {
        this.expenseService = mock(ExpensePortIn.class);
        GroupExpenseController controller = new GroupExpenseController(expenseService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createExpense_ShouldReturn201() throws Exception {

        Long groupId = 1L;
        ExpenseDTO expenseMock = new ExpenseDTO(100L, "Cena", 10L, groupId, 50.0);

        when(expenseService.createExpenseUseCase(any(ExpenseDTO.class)))
                .thenReturn(expenseMock);

        mockMvc.perform(post("/api/groups/" + groupId + "/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"title\": \"Cena\", " +
                        "\"payerId\": 10, " +
                        "\"groupId\": 1, " +
                        "\"totalAmount\": 50.0" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.expenseId").value(100))
                .andExpect(jsonPath("$.title").value("Cena"))
                .andExpect(jsonPath("$.totalAmount").value(50.0))
                .andExpect(jsonPath("$.payerId").value(10))
                .andExpect(jsonPath("$.groupId").value(1));
    }

    @Test
    void getExpensesByGroupId_ShouldReturnList() throws Exception {

        Long groupId = 1L;
        List<ExpenseDTO> expenses = List.of(
                new ExpenseDTO(1L, "Cena", 1L, groupId, 87.46));
        when(expenseService.listExpensesInGroupUseCase(groupId)).thenReturn(expenses);

        mockMvc.perform(get("/api/groups/" + groupId + "/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Cena"));
    }
}
