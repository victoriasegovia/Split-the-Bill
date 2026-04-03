package com.civica.splitthebill.domain.port.inbound;

import java.util.List;

import com.civica.splitthebill.application.dto.UserDTO;

public interface UserPortIn {
    UserDTO createUserUseCase(UserDTO userDTO, Long groupId);
    void addUserToGroup (Long userId, Long groupId);
    List<UserDTO> listUsersInGroupUseCase(Long groupId);
}
