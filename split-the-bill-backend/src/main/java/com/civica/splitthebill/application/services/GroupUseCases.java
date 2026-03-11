package com.civica.splitthebill.application.services;

import java.util.List;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
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
        Group groupCreated = groupRepository.save(group).orElseThrow(() -> new RuntimeException("Failed to save group"));

        return GroupDTOMapper.domainToDTO(groupCreated);
    }

    @Override
    public List<GroupDTO> listGroupsUseCase() {
        return groupRepository.findAll().stream().map(GroupDTOMapper::domainToDTO).toList();
    }

    @Override
    public GroupDTO listGroupByIdUseCase(Long groupId) {
        if (groupId == null) {
            throw new IllegalArgumentException("Group ID cannot be null");
        }
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        return GroupDTOMapper.domainToDTO(group);
    }

    @Override
    public void addUserToGroupUseCase(Long groupId, Long userId) {
        if (groupId == null) {
            throw new IllegalArgumentException("Group ID cannot be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        groupRepository.addUserToGroup(groupId, userId);
    }

    @Override
    public List<String> listGroupMembersUseCase(Long groupId) {
        if (groupId == null) {
            throw new IllegalArgumentException("Group ID cannot be null");
        }
        List<User> users = groupRepository.findUsersByGroupId(groupId);
        return users.stream().map(User::name).toList();
    }

}
