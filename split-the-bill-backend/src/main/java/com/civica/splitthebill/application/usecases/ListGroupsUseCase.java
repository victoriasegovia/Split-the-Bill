package com.civica.splitthebill.application.usecases;

import java.util.List;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.application.mapper.GroupDTOMapper;
import com.civica.splitthebill.domain.port.inbound.ListGroupsPortInbound;
import com.civica.splitthebill.domain.port.outbound.GroupPortOut;

public class ListGroupsUseCase implements ListGroupsPortInbound {

    private final GroupPortOut groupRepository;

    public ListGroupsUseCase(GroupPortOut groupRepository) {
        this.groupRepository = groupRepository;
    }
    
    @Override
    public List<GroupDTO> execute() {
        return groupRepository.findAll().stream()
                .map(GroupDTOMapper::domainToDTO)
                .toList();
    }
    
}
