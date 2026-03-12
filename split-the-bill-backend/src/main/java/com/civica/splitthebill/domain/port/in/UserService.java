package com.civica.splitthebill.domain.port.in;

import java.util.List;
import com.civica.splitthebill.application.dto.UserDTO;

public interface UserService {
    UserDTO createUserUseCase(UserDTO userDTO, Long groupId);
    List<UserDTO> listUsersInGroupUseCase(Long groupId);
}
