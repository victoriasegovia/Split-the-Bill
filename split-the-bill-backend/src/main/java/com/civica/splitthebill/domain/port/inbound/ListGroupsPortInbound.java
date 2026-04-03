package com.civica.splitthebill.domain.port.inbound;

import com.civica.splitthebill.application.dto.GroupDTO;
import java.util.List;

public interface ListGroupsPortInbound {
    List<GroupDTO> execute();
}
