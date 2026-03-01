package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import com.civica.splitthebill.domain.port.in.GroupUseCases;
import com.civica.splitthebill.domain.model.Group;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupUseCases groupUseCases;

    public GroupController(GroupUseCases groupUseCases) {
        this.groupUseCases = groupUseCases;
    }

    // This DTO should be in a separate file, but for simplicity, we can keep it
    // here for now!
    public static record CreateGroupRequest(
            @NotBlank @Size(min = 1, max = 100) String name) {
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateGroupRequest request) {
        groupUseCases.createGroupUseCase(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<Group> list() {
        return groupUseCases.listGroupsUseCase();
    }
    
}
