package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import com.civica.splitthebill.application.services.GroupService;
import com.civica.splitthebill.domain.port.out.UserRepository;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.RequestResponseMapper;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupRequest;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.GroupResponse;
import com.civica.splitthebill.application.dto.GroupDTO;
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
    private final UserRepository userRepository;

    public GroupController(GroupService groupService, UserRepository userRepository) {
        this.groupService = groupService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> create(@RequestBody GroupRequest request) {
        GroupDTO input = RequestResponseMapper.requestToDomainDTO(request);
        GroupDTO created = groupService.createGroupUseCase(input);
        List<String> memberNames = userRepository.findAllById(created.membersIds()).stream()
                .map(User::name)
                .toList();
        GroupResponse response = RequestResponseMapper.domainDTOToResponse(created, memberNames);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<GroupResponse> list() {
        List<GroupDTO> groups = groupService.listGroupsUseCase();
        return groups.stream()
                .map(groupDTO -> {
                    List<String> memberNames = userRepository.findAllById(groupDTO.membersIds()).stream()
                            .map(User::name)
                            .toList();
                    return RequestResponseMapper.domainDTOToResponse(groupDTO, memberNames);
                })
                .collect(Collectors.toList());
    }
    
}
