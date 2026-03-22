package com.civica.splitthebill.application.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.application.mapper.UserDTOMapper;
import com.civica.splitthebill.domain.exception.EntityAlreadyAssignedException;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.in.UserPortIn;
import com.civica.splitthebill.domain.port.out.GroupPortOut;
import com.civica.splitthebill.domain.port.out.UserPortOut;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserUseCases implements UserPortIn {

    private static final String GROUP = "Group";
    private static final String USER = "User";

    private final UserPortOut userRepository;
    private final GroupPortOut groupRepository;

    public UserUseCases(UserPortOut userRepository, GroupPortOut groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public UserDTO createUserUseCase(UserDTO userDTO, Long groupId) {

        groupRepository.findById(groupId)
                .orElseThrow(EntityNotFoundException::new);

        Set<Long> userGroupList = new HashSet<>();
        userGroupList.add(groupId);

        User newUser = new User(null, userDTO.name(), userGroupList, Set.of());
        User userCreated = userRepository.save(newUser);

        addUserToGroup(userCreated.userId(), groupId);

        return UserDTOMapper.domainToDTO(userCreated);
    }

    @Override
    public void addUserToGroup(Long userId, Long groupId) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(EntityNotFoundException::new);

        UseCaseUtils.checkExclusivity(userId, group.memberIds()::contains,
                (() -> new EntityAlreadyAssignedException(userId, USER, group.groupId(), GROUP)));

        Set<Long> newGroupMembers = new HashSet<>(group.memberIds());
        newGroupMembers.add(userId);

        Group newGroup = new Group(group.groupId(), group.name(), newGroupMembers, group.expenseIds());
        groupRepository.save(newGroup);
    }

    @Override
    public List<UserDTO> listUsersInGroupUseCase(Long groupId) {
        return userRepository.findAllByGroupId(groupId).stream()
                .map(UserDTOMapper::domainToDTO)
                .toList();
    }

}
