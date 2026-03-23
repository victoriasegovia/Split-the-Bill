package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.domain.port.in.UserPortIn;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.UserRequest;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.UserRequestResponseMapper;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.UserResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/groups/{groupId}/users")
public class GroupUserController {

    private static final String ID_NOT_NULL = "Id cannot be null";

    private final UserPortIn userService;

    public GroupUserController(UserPortIn userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsersByGroupId(@PathVariable Long groupId) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        List<UserDTO> users = userService.listUsersInGroupUseCase(groupId);

        List<UserResponse> response = users.stream()
                .map(UserRequestResponseMapper::domainDTOToResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUsersByGroupId(@PathVariable Long groupId,
            @Valid @RequestBody UserRequest request) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        UserDTO input = UserRequestResponseMapper.requestToDomainDTO(request);
        UserDTO created = userService.createUserUseCase(input, groupId);

        UserResponse response = UserRequestResponseMapper.domainDTOToResponse(created);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
