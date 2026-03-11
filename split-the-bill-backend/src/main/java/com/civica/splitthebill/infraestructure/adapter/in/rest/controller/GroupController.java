package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import com.civica.splitthebill.domain.port.in.GroupService;
import com.civica.splitthebill.domain.port.out.UserRepository;
import com.civica.splitthebill.domain.model.User;
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
    private final UserRepository userRepository;

    public GroupController(GroupService groupService, UserRepository userRepository) {
        this.groupService = groupService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> create(@RequestBody GroupRequest request) {
        GroupDTO input = RequestResponseMapper.requestToDomainDTO(request);
        GroupDTO created = groupService.createGroupUseCase(input);

        GroupResponse response = RequestResponseMapper.domainDTOToResponse(created, null, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<GroupResponse> getAll() {
        List<GroupDTO> groups = groupService.listGroupsUseCase();

        List<GroupResponse> response = groups.stream()
                .map(group -> RequestResponseMapper.domainDTOToResponse(group, group.memberNames()))
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public GroupResponse getById(@PathVariable Long id) {
        GroupDTO group = groupService.listGroupByIdUseCase(id);
        GroupResponse response = RequestResponseMapper.domainDTOToResponse(group);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
