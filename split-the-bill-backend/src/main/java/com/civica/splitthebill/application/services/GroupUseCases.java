package com.civica.splitthebill.application.services;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.application.mapper.GroupDTOMapper;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.in.GroupService;
import com.civica.splitthebill.domain.port.out.GroupRepository;

@Service
public class GroupUseCases implements GroupService {

    private final GroupRepository groupRepository;

    public GroupUseCases(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public GroupDTO createGroupUseCase(GroupDTO groupDTO) {

        Group group = GroupDTOMapper.dtoToDomain(groupDTO);
        Group groupCreated = groupRepository.save(group)
                .orElseThrow(() -> new RuntimeException("Failed to save group"));

        return GroupDTOMapper.domainToDTO(groupCreated);
    }

    @Override
    public List<GroupDTO> listGroupsUseCase() {
        return groupRepository.findAll().stream().map(GroupDTOMapper::domainToDTO).toList();
    }

    @Override
    public GroupDTO listGroupByIdUseCase(Long groupId) {

        Objects.requireNonNull(groupId, "Group Id cannot be null");
        return groupRepository.findById(groupId)
                .map(GroupDTOMapper::domainToDTO)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));
    }

    @Override
    public Set<String> listGroupMembersUseCase(Long groupId) {

        Objects.requireNonNull(groupId, "Group Id cannot be null");
        return groupRepository.findUsersByGroupId(groupId).stream()
                .map(User::name)
                .collect(Collectors.toSet());
    }

}
