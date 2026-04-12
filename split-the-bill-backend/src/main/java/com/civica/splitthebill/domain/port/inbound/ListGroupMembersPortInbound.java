package com.civica.splitthebill.domain.port.inbound;

import java.util.List;

public interface ListGroupMembersPortInbound {
    List<String> execute(Long groupId);
}
