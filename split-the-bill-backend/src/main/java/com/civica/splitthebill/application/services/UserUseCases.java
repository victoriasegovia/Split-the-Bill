package com.civica.splitthebill.application.services;

import java.util.Optional;
import java.util.Set;
import java.util.function.LongPredicate;
import java.util.function.Supplier;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.application.mapper.UserDTOMapper;
import com.civica.splitthebill.domain.exception.EntityAlreadyAssignedException;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.in.UserService;
import com.civica.splitthebill.domain.port.out.GroupRepository;
import com.civica.splitthebill.domain.port.out.UserRepository;

public class UserUseCases implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public UserUseCases(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public UserDTO createUserUseCase(UserDTO userDTO, Long groupId) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User user = UserDTOMapper.DTOToDomain(userDTO);

        checkExclusivity(user.id(), group.memberIds()::contains,
                () -> new EntityAlreadyAssignedException(user.id(), "User", group.groupId(), "Group"));

        Set<Long> userGroupList = user.groupIds();
        userGroupList.add(groupId);

        User newUser = new User(user.id(), user.name(), userGroupList, user.expenseIds());

        User userCreated = userRepository.save(newUser)
                .orElseThrow(() -> new RuntimeException("Failed to save user"));

        Set<Long> groupUserList = group.memberIds();
        groupUserList.add(user.id());
        Group updatedGroup = new Group(group.groupId(), group.name(), groupUserList, group.expenseIds());

        groupRepository.save(updatedGroup);

        return UserDTOMapper.domainToDTO(userCreated);
    }

    @Override
    public Set<UserDTO> listUsersInGroupUseCase(Long groupId) {
        throw new UnsupportedOperationException("Unimplemented method 'listUsersInGroupUseCase'");
    }

    private void checkExclusivity(Long id, LongPredicate condition, Supplier<RuntimeException> exception) {
        Optional.of(id)
                .filter(condition::test)
                .ifPresent(val -> {
                    throw exception.get();
                });
    }

}
