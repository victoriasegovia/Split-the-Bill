package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupRequestResponseMapper;

import jakarta.validation.Valid;

import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupRequest;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupResponse;
import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.domain.port.inbound.GroupPortIn;

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

    private final GroupPortIn groupService;

    public GroupController(GroupPortIn groupService) {
        this.groupService = groupService;
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

}
