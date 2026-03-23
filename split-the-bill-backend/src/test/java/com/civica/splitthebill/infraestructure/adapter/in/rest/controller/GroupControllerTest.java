package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.domain.port.in.GroupPortIn;
import com.civica.splitthebill.infraestructure.adapter.in.rest.handler.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

class GroupControllerTest {

    private MockMvc mockMvc;
    private GroupPortIn groupService; 

    @BeforeEach
    void setUp() {
 
        this.groupService = mock(GroupPortIn.class);

        GroupController groupController = new GroupController(groupService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(groupController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createGroup_ShouldReturn201() throws Exception {

        GroupDTO savedDto = new GroupDTO(1L, "Viaje a Granada");
        when(groupService.createGroupUseCase(any(GroupDTO.class))).thenReturn(savedDto);

        mockMvc.perform(post("/api/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Viaje a Granada\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.groupId").value(1))
                .andExpect(jsonPath("$.name").value("Viaje a Granada"));
    }

    @Test
    void getAllGroups_ShouldReturnList() throws Exception {

        List<GroupDTO> groups = List.of(
            new GroupDTO(1L, "Grupo A"),
            new GroupDTO(2L, "Grupo B")
        );
        when(groupService.listGroupsUseCase()).thenReturn(groups);
        when(groupService.listGroupMembersUseCase(anyLong())).thenReturn(List.of("User1"));

        mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Grupo A"));
    }

    @Test
    void getById_ShouldReturn404_WhenNotFound() throws Exception {

        when(groupService.listGroupByIdUseCase(1L)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/api/groups/1"))
                .andExpect(status().isNotFound());
    }
}
