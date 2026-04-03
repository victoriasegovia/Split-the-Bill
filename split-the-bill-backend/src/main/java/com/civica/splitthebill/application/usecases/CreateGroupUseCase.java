package com.civica.splitthebill.application.usecases;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.application.mapper.GroupDTOMapper;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.port.inbound.CreateGroupPortInbound;
import com.civica.splitthebill.domain.port.outbound.GroupPortOut;

@Service
public class CreateGroupUseCase implements CreateGroupPortInbound {

    private final GroupPortOut groupRepository;
    private final GroupDTOMapper groupDTOMapper = new GroupDTOMapper();

    public CreateGroupUseCase(GroupPortOut groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public GroupDTO execute(GroupDTO groupDTO) {

        Group group = new Group(groupDTO.name());
        Group groupCreated = groupRepository.save(group);

        return groupDTOMapper.domainToDTO(groupCreated);
    }

}
