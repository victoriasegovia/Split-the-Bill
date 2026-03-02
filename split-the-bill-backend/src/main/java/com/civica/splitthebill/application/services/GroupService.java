package com.civica.splitthebill.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.domain.exception.DuplicateGroupNameException;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.port.in.GroupUseCases;
import com.civica.splitthebill.domain.port.out.GroupRepository;

@Service
public class GroupService implements GroupUseCases {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group createGroupUseCase(String groupName) {
        if (groupRepository.findByName(groupName).isPresent()) {
            throw new DuplicateGroupNameException(groupName);
        }

        Group newGroup = new Group(null, groupName);
        return groupRepository.save(newGroup);
    }

    @Override
    public List<Group> listGroupsUseCase() {
        return groupRepository.findAll();
    }

}
