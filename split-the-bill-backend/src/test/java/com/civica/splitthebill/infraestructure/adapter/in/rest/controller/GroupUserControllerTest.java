package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.domain.port.inbound.UserPortIn;
import com.civica.splitthebill.infraestructure.adapter.in.rest.handler.GlobalExceptionHandler;

import org.springframework.http.MediaType;

class GroupUserControllerTest {

    private MockMvc mockMvc;
    private UserPortIn userService;

    @BeforeEach
    void setUp() {
        this.userService = Mockito.mock(UserPortIn.class);
        GroupUserController controller = new GroupUserController(userService);
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getUsersByGroupId_ShouldReturnList() throws Exception {

        Long groupId = 1L;
        UserDTO user = new UserDTO(10L, "Victoria");
        Mockito.when(userService.listUsersInGroupUseCase(groupId)).thenReturn(List.of(user));

        mockMvc.perform(get("/api/groups/" + groupId + "/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Victoria"));
        
        Mockito.verify(userService).listUsersInGroupUseCase(groupId);
    }

    @Test
    void createUserInGroup_ShouldReturn201() throws Exception {

        Long groupId = 1L;
        UserDTO savedUser = new UserDTO(10L, "Victoria");
        
        when(userService.createUserUseCase(any(UserDTO.class), eq(groupId)))
                .thenReturn(savedUser);

        mockMvc.perform(post("/api/groups/" + groupId + "/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Victoria\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(10)) 
                .andExpect(jsonPath("$.name").value("Victoria"));
        
        verify(userService).createUserUseCase(any(UserDTO.class), eq(groupId));
    }
}