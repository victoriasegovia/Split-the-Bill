package com.civica.splitthebill.application.usecases;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.application.mapper.GroupDTOMapper;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.inbound.GroupPortIn;
import com.civica.splitthebill.domain.port.outbound.GroupPortOut;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GroupUseCases implements GroupPortIn {

    private static final String ID_NOT_NULL = "Id cannot be null";

    private final GroupPortOut groupRepository;

    public GroupUseCases(GroupPortOut groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public GroupDTO createGroupUseCase(GroupDTO groupDTO) {
        Group group = new Group(groupDTO.name());
        Group groupCreated = groupRepository.save(group);

        return GroupDTOMapper.domainToDTO(groupCreated);
    }

    @Override
    public List<GroupDTO> listGroupsUseCase() {

        return groupRepository.findAll().stream()
                .map(GroupDTOMapper::domainToDTO)
                .toList();
    }

    @Override
    public GroupDTO listGroupByIdUseCase(Long groupId) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        return groupRepository.findById(groupId)
                .map(GroupDTOMapper::domainToDTO)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<String> listGroupMembersUseCase(Long groupId) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        return groupRepository.findUsersByGroupId(groupId).stream()
                .map(User::name)
                .toList();
    }

}
