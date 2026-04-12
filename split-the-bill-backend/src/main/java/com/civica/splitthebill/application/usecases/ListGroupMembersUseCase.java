package com.civica.splitthebill.application.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.inbound.ListGroupMembersPortInbound;
import com.civica.splitthebill.domain.port.outbound.GroupPortOut;

@Service
public class ListGroupMembersUseCase implements ListGroupMembersPortInbound {

    private final GroupPortOut groupRepository;

    public ListGroupMembersUseCase(GroupPortOut groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<String> execute(Long groupId) {
        return groupRepository.findUsersByGroupId(groupId).stream()
                .map(User::name)
                .toList();
    }
}
