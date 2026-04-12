package com.civica.splitthebill.application.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.application.mapper.UserDTOMapper;
import com.civica.splitthebill.domain.port.inbound.ListUsersInGroupPortInbound;
import com.civica.splitthebill.domain.port.outbound.UserPortOut;

@Service
public class ListUsersInGroupUseCase implements ListUsersInGroupPortInbound {

    private final UserPortOut userRepository;
    private final UserDTOMapper userDTOMapper;

    public ListUsersInGroupUseCase(UserPortOut userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

    @Override
    public List<UserDTO> execute(Long groupId) {
        return userRepository.findAllByGroupId(groupId).stream()
                .map(userDTOMapper::domainToDTO)
                .toList();
    }
}
