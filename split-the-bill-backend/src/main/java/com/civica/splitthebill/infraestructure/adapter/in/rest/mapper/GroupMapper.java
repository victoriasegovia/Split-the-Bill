package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;

@Component
public final class GroupMapper {

    private GroupMapper() {
    }

    public static Group entitytoDomain(GroupEntity groupEntity) {

        List<User> members = groupEntity.getMembers().stream()
                .map(userEntity -> new User(userEntity.getId(), userEntity.getName()))
                .collect(Collectors.toList());

        Group group = new Group(groupEntity.getId(), groupEntity.getName(), members);
        
        return group;
    }

    public static GroupEntity domaintoEntity(Group group) {

        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(group.id());
        groupEntity.setName(group.name());

        List<UserEntity> memberEntities = group.members().stream()
                .map(user -> new UserEntity(user.id(), user.name()))
                .collect(Collectors.toList());
        groupEntity.setMembers(memberEntities);


        return groupEntity;
    }
}
