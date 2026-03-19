package com.civica.splitthebill.domain.port.in;

import java.util.Set;

import com.civica.splitthebill.application.dto.UserDTO;

public interface UserPortIn {
    UserDTO createUserUseCase(UserDTO userDTO, Long groupId);
    void addUserToGroup (Long userId, Long groupId);
    Set<UserDTO> listUsersInGroupUseCase(Long groupId);
}
