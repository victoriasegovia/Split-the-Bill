package com.civica.splitthebill.application.usecases;

import java.util.HashSet;
import java.util.Set;

import com.civica.splitthebill.domain.model.User;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.application.mapper.UserDTOMapper;
import com.civica.splitthebill.domain.port.inbound.CreateUserPortInbound;
import com.civica.splitthebill.domain.port.outbound.GroupPortOut;
import com.civica.splitthebill.domain.port.outbound.UserPortOut;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CreateUserUseCase implements CreateUserPortInbound {

    private final UserPortOut userRepository;
    private final GroupPortOut groupRepository;
    private final UserDTOMapper userDTOMapper = new UserDTOMapper();

    public CreateUserUseCase(UserPortOut userRepository, GroupPortOut groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public UserDTO execute(UserDTO userDTO, Long groupId) {
        groupRepository.findById(groupId)
                .orElseThrow(EntityNotFoundException::new);

        Set<Long> userGroupList = new HashSet<>();
        userGroupList.add(groupId);

        User newUser = new User(null, userDTO.name(), userGroupList, new HashSet<>());
        User userCreated = userRepository.save(newUser);

        return userDTOMapper.domainToDTO(userCreated);
    }
}
