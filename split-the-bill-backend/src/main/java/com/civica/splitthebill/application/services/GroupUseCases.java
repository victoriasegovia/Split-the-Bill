package com.civica.splitthebill.application.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.application.mapper.GroupDTOMapper;
import com.civica.splitthebill.domain.exception.DuplicateGroupNameException;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.in.GroupService;
import com.civica.splitthebill.domain.port.out.GroupRepository;
import com.civica.splitthebill.domain.port.out.UserRepository;

@Service
public class GroupUseCases implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupUseCases(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public GroupDTO createGroupUseCase(GroupDTO groupDTO) {
        if (groupRepository.findByName(groupDTO.name()).isPresent()) {
            throw new DuplicateGroupNameException(groupDTO.name());
        }

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
        
        validateId(groupId);
        
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        return GroupDTOMapper.domainToDTO(group);
    }

    @Override
    public void addUserToGroupUseCase(Long groupId, Long userId) {

        validateId(groupId);
        validateId(userId);

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + groupId));

        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        List<Long> updatedMembers = new ArrayList<>(group.memberIds());
        updatedMembers.add(userId);

        Group updatedGroup = new Group(group.id(), group.name(), updatedMembers, group.expenseIds());
        groupRepository.save(updatedGroup);
    }

    @Override
    public List<String> listGroupMembersUseCase(Long groupId) {
        
        validateId(groupId);

        List<User> users = groupRepository.findUsersByGroupId(groupId);
        return users.stream().map(User::name).toList();
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
    }

}
