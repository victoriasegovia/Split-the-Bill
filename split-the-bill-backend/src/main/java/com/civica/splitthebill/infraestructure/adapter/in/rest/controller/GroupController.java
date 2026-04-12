package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupRequestResponseMapper;

import jakarta.validation.Valid;

import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupRequest;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupResponse;
import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.domain.port.inbound.CreateGroupPortInbound;
import com.civica.splitthebill.domain.port.inbound.ListGroupByIdPortInbound;
import com.civica.splitthebill.domain.port.inbound.ListGroupMembersPortInbound;
import com.civica.splitthebill.domain.port.inbound.ListGroupsPortInbound;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private static final String ID_NOT_NULL = "Id cannot be null";

    private final CreateGroupPortInbound createGroupUseCase;
    private final ListGroupsPortInbound listGroupsUseCase;
    private final ListGroupByIdPortInbound listGroupByIdUseCase;
    private final ListGroupMembersPortInbound listGroupMembersUseCase;

    public GroupController(CreateGroupPortInbound createGroupUseCase,
            ListGroupsPortInbound listGroupsUseCase,
            ListGroupByIdPortInbound listGroupByIdUseCase,
            ListGroupMembersPortInbound listGroupMembersUseCase) {
        this.createGroupUseCase = createGroupUseCase;
        this.listGroupsUseCase = listGroupsUseCase;
        this.listGroupByIdUseCase = listGroupByIdUseCase;
        this.listGroupMembersUseCase = listGroupMembersUseCase;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> create(@Valid @RequestBody GroupRequest request) {

        GroupDTO input = GroupRequestResponseMapper.requestToDomainDTO(request);
        GroupDTO created = createGroupUseCase.execute(input);
        List<String> members = new ArrayList<>();

        GroupResponse response = GroupRequestResponseMapper.domainDTOToResponse(created, members);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAll() {

        List<GroupDTO> groups = listGroupsUseCase.execute();

        List<GroupResponse> response = groups.stream()
                .map(group -> {
                    List<String> memberNames = listGroupMembersUseCase.execute(group.groupId());
                    return GroupRequestResponseMapper.domainDTOToResponse(group, memberNames);
                })
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> getById(@PathVariable Long groupId) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        GroupDTO group = listGroupByIdUseCase.execute(groupId);
        List<String> memberNames = listGroupMembersUseCase.execute(groupId);
        GroupResponse response = GroupRequestResponseMapper.domainDTOToResponse(group, memberNames);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
