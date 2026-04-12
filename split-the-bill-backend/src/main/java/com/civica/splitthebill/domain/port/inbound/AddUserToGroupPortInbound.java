package com.civica.splitthebill.domain.port.inbound;

public interface AddUserToGroupPortInbound {
    void execute(Long userId, Long groupId);
}
