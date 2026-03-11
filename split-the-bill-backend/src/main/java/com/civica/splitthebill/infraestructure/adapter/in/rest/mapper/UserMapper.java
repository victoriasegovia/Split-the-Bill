package com.civica.splitthebill.infraestructure.adapter.in.rest.mapper;

import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.domain.model.User;

public final class UserMapper {
    
    public UserMapper() {
    }

    public static UserEntity domaintoEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.id());
        userEntity.setName(user.name());
        userEntity.setGroupIds(user.groupIds());
        userEntity.setExpenseIds(user.expenseIds());
        return userEntity;
    }

    public static User entitytoDomain(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getName(), userEntity.getGroupIds(), userEntity.getExpenseIds());
    }
}
