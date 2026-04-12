package com.civica.splitthebill.application.usecases;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.domain.exception.EntityAlreadyAssignedException;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.port.inbound.AddUserToGroupPortInbound;
import com.civica.splitthebill.domain.port.outbound.GroupPortOut;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AddUserToGroupUseCase implements AddUserToGroupPortInbound {

    private static final String GROUP = "Group";
    private static final String USER = "User";

    private final GroupPortOut groupRepository;

    public AddUserToGroupUseCase(GroupPortOut groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void execute(Long userId, Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(EntityNotFoundException::new);

        Set<Long> newGroupMembers = new HashSet<>(group.memberIds());
        UseCaseUtils.checkExclusivity(userId, newGroupMembers::contains,
                () -> new EntityAlreadyAssignedException(userId, USER, group.groupId(), GROUP));

        newGroupMembers.add(userId);

        Group updated = new Group(group.groupId(), group.name(), newGroupMembers, group.expenseIds());
        groupRepository.save(updated);
    }
}
