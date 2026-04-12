package com.civica.splitthebill.domain.port.inbound;

import java.util.List;

import com.civica.splitthebill.application.dto.UserDTO;

public interface ListUsersInGroupPortInbound {
    List<UserDTO> execute(Long groupId);
}
