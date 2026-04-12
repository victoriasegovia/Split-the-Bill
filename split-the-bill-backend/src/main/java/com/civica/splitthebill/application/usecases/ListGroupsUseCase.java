package com.civica.splitthebill.application.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.application.mapper.GroupDTOMapper;
import com.civica.splitthebill.domain.port.inbound.ListGroupsPortInbound;
import com.civica.splitthebill.domain.port.outbound.GroupPortOut;

@Service
public class ListGroupsUseCase implements ListGroupsPortInbound {

    private final GroupPortOut groupRepository;
    private final GroupDTOMapper groupDTOMapper;

    public ListGroupsUseCase(GroupPortOut groupRepository, GroupDTOMapper groupDTOMapper) {
        this.groupRepository = groupRepository;
        this.groupDTOMapper = groupDTOMapper;
    }
    
    @Override
    public List<GroupDTO> execute() {
        return groupRepository.findAll().stream()
                .map(groupDTOMapper::domainToDTO)
                .toList();
    }
    
}
