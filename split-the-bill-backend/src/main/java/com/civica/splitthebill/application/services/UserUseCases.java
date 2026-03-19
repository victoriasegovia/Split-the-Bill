package com.civica.splitthebill.application.services;

import java.util.Objects;
import java.util.Set;
import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.application.mapper.UserDTOMapper;
import com.civica.splitthebill.domain.exception.EntityAlreadyAssignedException;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.in.UserPortIn;
import com.civica.splitthebill.domain.port.out.GroupPortOut;
import com.civica.splitthebill.domain.port.out.UserPortOut;

public class UserUseCases implements UserPortIn {

    private static final String GROUP_NOT_FOUND = "Group not found";
    private static final String ID_NOT_NULL = "Id cannot be null";
    private static final String GROUP = "Group";
    private static final String USER = "User";
    private static final String FAIL_TO_SAVE_USER = "Failed to save user";

    private final UserPortOut userRepository;
    private final GroupPortOut groupRepository;

    public UserUseCases(UserPortOut userRepository, GroupPortOut groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public UserDTO createUserUseCase(UserDTO userDTO, Long groupId) {

        Objects.requireNonNull(groupId, ID_NOT_NULL);
        User user = UserDTOMapper.DTOToDomain(userDTO);
        groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException(GROUP_NOT_FOUND));

        Set<Long> userGroupList = user.groupIds();
        userGroupList.add(groupId);

        User newUser = new User(user.userId(), user.name(), userGroupList, user.expenseIds());
        User userCreated = userRepository.save(newUser)
                .orElseThrow(() -> new RuntimeException(FAIL_TO_SAVE_USER));

        addUserToGroup(userCreated.userId(), groupId);

        return UserDTOMapper.domainToDTO(userCreated);
    }

    @Override
    public void addUserToGroup(Long userId, Long groupId) {

        Objects.requireNonNull(groupId, ID_NOT_NULL);
        Objects.requireNonNull(userId, ID_NOT_NULL);

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException(GROUP_NOT_FOUND));

        UseCaseUtils.checkExclusivity(userId, group.memberIds()::contains,
                (() -> new EntityAlreadyAssignedException(userId, USER, group.groupId(), GROUP)));

        Set<Long> newGroupMembers = group.memberIds();
        newGroupMembers.add(userId);

        Group newGroup = new Group(group.groupId(), group.name(), newGroupMembers, group.expenseIds());
        groupRepository.save(newGroup);
    }

    @Override
    public Set<UserDTO> listUsersInGroupUseCase(Long groupId) {
        throw new UnsupportedOperationException("Unimplemented method 'listUsersInGroupUseCase'");
    }

}
