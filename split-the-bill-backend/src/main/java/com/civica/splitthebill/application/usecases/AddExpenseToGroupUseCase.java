package com.civica.splitthebill.application.usecases;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.civica.splitthebill.domain.exception.EntityAlreadyAssignedException;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.port.inbound.AddExpenseToGroupPortInbound;
import com.civica.splitthebill.domain.port.outbound.GroupPortOut;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AddExpenseToGroupUseCase implements AddExpenseToGroupPortInbound {

    private static final String GROUP = "Group";
    private static final String EXPENSE = "Expense";

    private final GroupPortOut groupRepository;

    public AddExpenseToGroupUseCase(GroupPortOut groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void execute(Long expenseId, Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(EntityNotFoundException::new);

        Set<Long> expenseIds = new HashSet<>(group.expenseIds());
        UseCaseUtils.checkExclusivity(expenseId, expenseIds::contains,
                () -> new EntityAlreadyAssignedException(expenseId, EXPENSE, groupId, GROUP));

        expenseIds.add(expenseId);

        Group updated = new Group(group.groupId(), group.name(), group.memberIds(), expenseIds);
        groupRepository.save(updated);
    }
}
