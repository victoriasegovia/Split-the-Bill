package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.UserPortOut;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper.UserMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaExpenseRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaGroupRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaUserRepository;

@Repository
public class UserPersistanceAdapter implements UserPortOut {

    private final JpaUserRepository jpaUserRepository;
    private final JpaGroupRepository jpaGroupRepository;
    private final JpaExpenseRepository jpaExpenseRepository;

    public UserPersistanceAdapter(JpaUserRepository jpaUserRepository, JpaGroupRepository jpaGroupRepository, JpaExpenseRepository jpaExpenseRepository) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaExpenseRepository = jpaExpenseRepository;
        this.jpaGroupRepository = jpaGroupRepository;
    }

    @Override
    public User save(User user) {
        List<GroupEntity> groups = jpaGroupRepository.findAllById(user.groupIds());
        List<ExpenseEntity> expenses = jpaExpenseRepository.findAllById(user.expenseIds());

        UserEntity userEntity = new UserEntity(user.userId(), user.name(), List.copyOf(groups), List.copyOf(expenses));
        jpaUserRepository.save(userEntity);

        return UserMapper.entityToDomain(userEntity);
    }

    @Override
    public Optional<User> findById(Long userId) {
        Optional<UserEntity> userEntity = jpaUserRepository.findById(userId);
        return userEntity.map(UserMapper::entityToDomain);
    }

    @Override
    public Optional<User> findByIdInGroup(Long id, Long groupId) {
        Optional<UserEntity> userEntity = jpaUserRepository.findByIdAndGroups_Id(id, groupId);
        return userEntity.map(UserMapper::entityToDomain);
    }

    @Override
    public List<User> findAllByGroupId(Long groupId) {
        List<UserEntity> userEntities = jpaUserRepository.findByGroups_Id(groupId);
        return userEntities.stream()
                .map(UserMapper::entityToDomain)
                .toList();
    }
    
}
