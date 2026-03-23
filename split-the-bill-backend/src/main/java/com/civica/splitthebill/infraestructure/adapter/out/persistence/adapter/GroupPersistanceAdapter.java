package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.GroupPortOut;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper.GroupMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper.UserMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaExpenseRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaGroupRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GroupPersistanceAdapter implements GroupPortOut {

    private final JpaGroupRepository jpaGroupRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaExpenseRepository jpaExpenseRepository;

    public GroupPersistanceAdapter(JpaGroupRepository jpaGroupRepository, JpaUserRepository jpaUserRepository,
            JpaExpenseRepository jpaExpenseRepository) {
        this.jpaGroupRepository = jpaGroupRepository;
        this.jpaUserRepository = jpaUserRepository;
        this.jpaExpenseRepository = jpaExpenseRepository;
    }

    @Override
    public Group save(Group group) {

        List<UserEntity> memberEntities = Optional.ofNullable(group.memberIds())
                .map(jpaUserRepository::findAllById)
                .orElseGet(List::of);

        List<ExpenseEntity> expenseEntities = Optional.ofNullable(group.expenseIds())
                .map(jpaExpenseRepository::findAllById)
                .orElseGet(List::of);

        GroupEntity groupEntity = new GroupEntity(group.groupId(), group.name(), memberEntities, expenseEntities);

        GroupEntity savedEntity = jpaGroupRepository.save(groupEntity);

        return GroupMapper.entitytoDomain(savedEntity);
    }

    @Override
    public Optional<Group> findByName(String name) {
        Optional<GroupEntity> groupEntity = jpaGroupRepository.findByName(name);
        return groupEntity.map(GroupMapper::entitytoDomain);
    }

    @Override
    public List<Group> findAll() {
        return jpaGroupRepository.findAll().stream()
                .map(GroupMapper::entitytoDomain)
                .toList();
    }

    @Override
    public List<User> findUsersByGroupId(Long groupId) {
        List<UserEntity> users = jpaUserRepository.findByGroups_Id(groupId);

        return users.stream()
                .map(UserMapper::entityToDomain)
                .toList();
    }

    @Override
    public Optional<Group> findById(Long id) {
        Optional<GroupEntity> groupEntity = jpaGroupRepository.findById(id);
        return groupEntity.map(GroupMapper::entitytoDomain);
    }

}
