package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import java.util.List;
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

        List<Long> membersIds = groupEntity.getMembers().stream()
                .map((UserEntity u) -> u.getId())
                .toList();

        List<Long> expenseIds = groupEntity.getExpenses().stream()
                .map(ExpenseEntity::getId)
                .toList();
        
        return new Group(groupEntity.getId(), groupEntity.getName(), membersIds, expenseIds);
    }

    public static GroupEntity domaintoEntity(Group group, List<UserEntity> members, List<ExpenseEntity> expenses) {

        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(group.id());
        groupEntity.setName(group.name());
        groupEntity.setMembers(members);
        groupEntity.setExpenses(expenses);

        return groupEntity;
    }
}
