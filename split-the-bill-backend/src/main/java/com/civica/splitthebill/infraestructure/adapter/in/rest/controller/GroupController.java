package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import com.civica.splitthebill.domain.port.in.ExpensePortIn;
import com.civica.splitthebill.domain.port.in.GroupPortIn;
import com.civica.splitthebill.domain.port.in.UserPortIn;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupRequestResponseMapper;

import jakarta.validation.Valid;

import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupRequest;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupResponse;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.UserRequestResponseMapper;
import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.application.dto.UserDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.ExpenseRequestResponseMapper;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.ExpenseResponse;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.ExpenseRequest;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.UserResponse;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.UserRequest;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private static final String ID_NOT_NULL = "Id cannot be null";

    private final GroupPortIn groupService;
    private final UserPortIn userService;
    private final ExpensePortIn expenseService;

    public GroupController(GroupPortIn groupService, UserPortIn userService, ExpensePortIn expenseService) {
        this.groupService = groupService;
        this.userService = userService;
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> create(@Valid @RequestBody GroupRequest request) {

        GroupDTO input = GroupRequestResponseMapper.requestToDomainDTO(request);
        GroupDTO created = groupService.createGroupUseCase(input);
        List<String> members = new ArrayList<>();

        GroupResponse response = GroupRequestResponseMapper.domainDTOToResponse(created, members);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAll() {

        List<GroupDTO> groups = groupService.listGroupsUseCase();

        List<GroupResponse> response = groups.stream()
                .map(group -> {
                    List<String> memberNames = groupService.listGroupMembersUseCase(group.groupId());
                    return GroupRequestResponseMapper.domainDTOToResponse(group, memberNames);
                })
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> getById(@PathVariable Long groupId) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        GroupDTO group = groupService.listGroupByIdUseCase(groupId);
        List<String> memberNames = groupService.listGroupMembersUseCase(groupId);
        GroupResponse response = GroupRequestResponseMapper.domainDTOToResponse(group, memberNames);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{groupId}/users")
    public ResponseEntity<List<UserResponse>> getUsersByGroupId(@PathVariable Long groupId) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        List<UserDTO> users = userService.listUsersInGroupUseCase(groupId);

        List<UserResponse> response = users.stream()
                .map(UserRequestResponseMapper::domainDTOToResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{groupId}/users")
    public ResponseEntity<UserResponse> createUsersByGroupId(@PathVariable Long groupId, @Valid @RequestBody UserRequest request) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        UserDTO input = UserRequestResponseMapper.requestToDomainDTO(request);
        UserDTO created = userService.createUserUseCase(input, groupId);

        UserResponse response = UserRequestResponseMapper.domainDTOToResponse(created);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{groupId}/expenses")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByGroupId(@PathVariable Long groupId) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        List<ExpenseDTO> expenses = expenseService.listExpensesInGroupUseCase(groupId);
        List<ExpenseResponse> response = expenses.stream()
                .map(ExpenseRequestResponseMapper::domainDTOToResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{groupId}/expenses")
    public ResponseEntity<ExpenseResponse> createExpenseByGroupId(@PathVariable Long groupId, @Valid @RequestBody ExpenseRequest request) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        ExpenseDTO input = ExpenseRequestResponseMapper.requestToDomainDTO(request);
        ExpenseDTO created = expenseService.createExpenseUseCase(input);

        ExpenseResponse response = ExpenseRequestResponseMapper.domainDTOToResponse(created);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
