package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import com.civica.splitthebill.domain.port.in.GroupService;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupRequest;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupResponse;
import com.civica.splitthebill.infraestructure.adapter.in.rest.mapper.GroupDomainMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> create(@Valid @RequestBody GroupRequest request) {
        Group created = groupService.createGroupUseCase(request.name());
        GroupResponse response = GroupDomainMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<GroupResponse> list() {
        return groupService.listGroupsUseCase().stream()
                .map(GroupDomainMapper::toResponse)
                .collect(Collectors.toList());
    }
    
}
