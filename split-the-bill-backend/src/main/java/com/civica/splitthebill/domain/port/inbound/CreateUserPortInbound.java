package com.civica.splitthebill.domain.port.inbound;

import com.civica.splitthebill.application.dto.UserDTO;

public interface CreateUserPortInbound {
    UserDTO execute(UserDTO userDTO, Long groupId);
}
