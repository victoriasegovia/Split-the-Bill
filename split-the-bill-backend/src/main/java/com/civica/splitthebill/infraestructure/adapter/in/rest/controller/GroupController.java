package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import com.civica.splitthebill.domain.port.in.GroupPortIn;
import com.civica.splitthebill.domain.port.in.UserPortIn;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupRequestResponseMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;

import jakarta.validation.Valid;

import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupRequest;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupResponse;
import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.application.dto.UserDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupPortIn groupService;
    private final UserPortIn userService;

    public GroupController(GroupPortIn groupService, UserPortIn userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> create(@Valid @RequestBody GroupRequest request) {
        
        GroupDTO input = GroupRequestResponseMapper.requestToDomainDTO(request);
        GroupDTO created = groupService.createGroupUseCase(input);

        GroupResponse response = GroupRequestResponseMapper.domainDTOToResponse(created, Set.of());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAll() {

        Set<GroupDTO> groups = groupService.listGroupsUseCase();
        
        List<GroupResponse> response = groups.stream()
                .map(group -> {
                    Set<String> memberNames = groupService.listGroupMembersUseCase(group.groupId());
                    return GroupRequestResponseMapper.domainDTOToResponse(group, memberNames);
                })
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getById(@PathVariable Long id) {

        GroupDTO group = groupService.listGroupByIdUseCase(id);
        Set<String> memberNames = groupService.listGroupMembersUseCase(id);
        GroupResponse response = GroupRequestResponseMapper.domainDTOToResponse(group, memberNames);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<UserEntity> getUsersByGroupId(@PathVariable Long groupId) {
        Set<UserDTO> users = userService.listUsersInGroupUseCase(groupId);

    }
}
