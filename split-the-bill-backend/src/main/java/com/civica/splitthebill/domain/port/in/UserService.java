package com.civica.splitthebill.domain.port.in;

import java.util.Set;

import com.civica.splitthebill.application.dto.UserDTO;

public interface UserService {
    UserDTO createUserUseCase(UserDTO userDTO, Long groupId);
    Set<UserDTO> listUsersInGroupUseCase(Long groupId);
}
