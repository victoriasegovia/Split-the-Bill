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
import com.civica.splitthebill.domain.port.in.GroupPortIn;
import com.civica.splitthebill.domain.port.out.GroupPortOut;

@Service
public class GroupUseCases implements GroupPortIn {

    private static final String FAIL_TO_SAVE_GROUP = "Failed to save group";
    private static final String ID_NOT_NULL = "Group Id cannot be null";
    private static final String GROUP_NOT_FOUND = "Group not found";

    private final GroupPortOut groupRepository;

    public GroupUseCases(GroupPortOut groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public GroupDTO createGroupUseCase(GroupDTO groupDTO) {
        Group group = GroupDTOMapper.dtoToDomain(groupDTO);
        Group groupCreated = groupRepository.save(group)
                .orElseThrow(() -> new RuntimeException(FAIL_TO_SAVE_GROUP));

        return GroupDTOMapper.domainToDTO(groupCreated);
    }

    @Override
    public Set<GroupDTO> listGroupsUseCase() {

        return groupRepository.findAll().stream()
                .map(GroupDTOMapper::domainToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public GroupDTO listGroupByIdUseCase(Long groupId) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        return groupRepository.findById(groupId)
                .map(GroupDTOMapper::domainToDTO)
                .orElseThrow(() -> new RuntimeException(GROUP_NOT_FOUND));
    }

    @Override
    public Set<String> listGroupMembersUseCase(Long groupId) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        return groupRepository.findUsersByGroupId(groupId).stream()
                .map(User::name)
                .collect(Collectors.toSet());
    }

}
