package com.civica.splitthebill.application.services;

import java.util.List;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.application.mapper.UserDTOMapper;
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
        User userCreated = userRepository.save(user)
                .orElseThrow(() -> new RuntimeException("Failed to save user"));

        group.addMember(user.id());
        groupRepository.save(group);

        return UserDTOMapper.domainToDTO(userCreated);
    }

    @Override
    public List<UserDTO> listUsersInGroupUseCase(Long groupId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listUsersInGroupUseCase'");
    }

}
