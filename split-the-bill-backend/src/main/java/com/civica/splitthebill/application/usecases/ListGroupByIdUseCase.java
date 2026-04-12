package com.civica.splitthebill.application.usecases;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.application.mapper.GroupDTOMapper;
import com.civica.splitthebill.domain.port.inbound.ListGroupByIdPortInbound;
import com.civica.splitthebill.domain.port.outbound.GroupPortOut;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ListGroupByIdUseCase implements ListGroupByIdPortInbound {

    private static final String ID_NOT_NULL = "Id cannot be null";

    private final GroupPortOut groupRepository;
    private final GroupDTOMapper groupDTOMapper = new GroupDTOMapper();

    public ListGroupByIdUseCase(GroupPortOut groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public GroupDTO execute(Long groupId) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        return groupRepository.findById(groupId)
                .map(groupDTOMapper::domainToDTO)
                .orElseThrow(EntityNotFoundException::new);
    }
}
