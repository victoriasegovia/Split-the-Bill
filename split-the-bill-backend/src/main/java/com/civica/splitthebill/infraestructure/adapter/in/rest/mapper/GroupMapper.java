package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;

@Component
public final class GroupMapper {

    private GroupMapper() {
    }

    public static Group entitytoDomain(GroupEntity groupEntity) {

        Set<Long> membersIds = groupEntity.getMembers().stream()
                .map((UserEntity u) -> u.getId())
                .collect(Collectors.toSet());

        Set<Long> expenseIds = groupEntity.getExpenses().stream()
                .map(ExpenseEntity::getId)
                .collect(Collectors.toSet());
        
        return new Group(groupEntity.getId(), groupEntity.getName(), membersIds, expenseIds);
    }

    public static GroupEntity domaintoEntity(Group group, Set<UserEntity> members, Set<ExpenseEntity> expenses) {

        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(group.id());
        groupEntity.setName(group.name());
        groupEntity.setMembers(members);
        groupEntity.setExpenses(expenses);

        return groupEntity;
    }
}
