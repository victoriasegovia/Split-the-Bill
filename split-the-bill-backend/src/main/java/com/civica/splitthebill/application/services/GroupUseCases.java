package com.civica.splitthebill.application.services;

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

        List<User> members = userRepository.findAllById(groupDTO.membersIds());
        Group group = GroupDTOMapper.dtoToDomain(groupDTO, members);
        Group saved = groupRepository.save(group);

        return GroupDTOMapper.domainToDTO(saved);
    }

    @Override
    public List<Group> listGroupsUseCase() {
        return groupRepository.findAll();
    }

    @Override
    public void addUserToGroupUseCase(Long groupId, Long userId) {
        groupRepository.addUserToGroup(groupId, userId);
    }

    @Override
    public void removeUserFromGroupUseCase(Long groupId, Long userId) {
        groupRepository.removeUserFromGroup(groupId, userId);
    }

}
