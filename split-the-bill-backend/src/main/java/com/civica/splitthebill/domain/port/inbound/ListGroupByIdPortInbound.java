package com.civica.splitthebill.domain.port.inbound;

import com.civica.splitthebill.application.dto.GroupDTO;

public interface ListGroupByIdPortInbound {
    GroupDTO execute(Long groupId);
}
