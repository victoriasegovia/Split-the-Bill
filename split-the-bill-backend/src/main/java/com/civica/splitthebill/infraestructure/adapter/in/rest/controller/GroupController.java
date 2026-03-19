package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import com.civica.splitthebill.domain.port.in.GroupPortIn;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.RequestResponseMapper;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupRequest;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupResponse;
import com.civica.splitthebill.application.dto.GroupDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupPortIn groupService;

    public GroupController(GroupPortIn groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> create(@RequestBody GroupRequest request) {
        GroupDTO input = RequestResponseMapper.requestToDomainDTO(request);
        GroupDTO created = groupService.createGroupUseCase(input);

        GroupResponse response = RequestResponseMapper.domainDTOToResponse(created, Set.of());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Set<GroupResponse>> getAll() {

        Set<GroupDTO> groups = groupService.listGroupsUseCase();
        
        Set<GroupResponse> response = groups.stream()
                .map(group -> {
                    Set<String> memberNames = groupService.listGroupMembersUseCase(group.groupId());
                    return RequestResponseMapper.domainDTOToResponse(group, memberNames);
                })
                .collect(Collectors.toSet());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getById(@PathVariable Long id) {

        GroupDTO group = groupService.listGroupByIdUseCase(id);
        Set<String> memberNames = groupService.listGroupMembersUseCase(id);
        GroupResponse response = RequestResponseMapper.domainDTOToResponse(group, memberNames);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
