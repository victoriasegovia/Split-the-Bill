package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import com.civica.splitthebill.domain.port.in.GroupService;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.RequestResponseMapper;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupRequest;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupResponse;
import com.civica.splitthebill.application.dto.GroupDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> create(@RequestBody GroupRequest request) {
        GroupDTO input = RequestResponseMapper.requestToDomainDTO(request);
        GroupDTO created = groupService.createGroupUseCase(input);

        GroupResponse response = RequestResponseMapper.domainDTOToResponse(created, List.of());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAll() {

        List<GroupDTO> groups = groupService.listGroupsUseCase();
        
        List<GroupResponse> response = groups.stream()
                .map(group -> {
                    List<String> memberNames = groupService.listGroupMembersUseCase(group.id());
                    GroupResponse groupResponse = RequestResponseMapper.domainDTOToResponse(group, memberNames);
                    return groupResponse;
                })
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getById(@PathVariable Long id) {

        GroupDTO group = groupService.listGroupByIdUseCase(id);
        List<String> memberNames = groupService.listGroupMembersUseCase(id);
        GroupResponse response = RequestResponseMapper.domainDTOToResponse(group, memberNames);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
