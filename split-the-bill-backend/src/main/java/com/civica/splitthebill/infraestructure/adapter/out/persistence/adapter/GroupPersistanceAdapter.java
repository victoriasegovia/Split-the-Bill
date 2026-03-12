package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.GroupRepository;
import com.civica.splitthebill.infraestructure.adapter.in.rest.mapper.GroupMapper;
import com.civica.splitthebill.infraestructure.adapter.in.rest.mapper.UserMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.ExpenseEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaExpenseRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaGroupRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GroupPersistanceAdapter implements GroupRepository {

    private final JpaGroupRepository jpaGroupRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaExpenseRepository jpaExpenseRepository;

    public GroupPersistanceAdapter(JpaGroupRepository jpaGroupRepository, JpaUserRepository jpaUserRepository, JpaExpenseRepository jpaExpenseRepository) {
        this.jpaGroupRepository = jpaGroupRepository;
        this.jpaUserRepository = jpaUserRepository;
        this.jpaExpenseRepository = jpaExpenseRepository;
    }

    @Override
    public Optional<Group> save(Group group) {
        
        List<UserEntity> memberEntities = jpaUserRepository.findAllById(group.memberIds());
        List<ExpenseEntity> expenseEntities = jpaExpenseRepository.findAllByGroupId(group.id());

        GroupEntity groupEntity = GroupMapper.domaintoEntity(group, memberEntities, expenseEntities);
        GroupEntity savedEntity = jpaGroupRepository.save(groupEntity);

        return Optional.of(GroupMapper.entitytoDomain(savedEntity));
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
        Optional<GroupEntity> groupEntity = jpaGroupRepository.findById(groupId);

        return groupEntity
                .map(GroupEntity::getMembers)
                .orElse(List.of())
                .stream()
                .map(UserMapper::entitytoDomain)
                .toList();
    }

    @Override
    public Optional<Group> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Group ID cannot be null");
        }
        Optional<GroupEntity> groupEntity = jpaGroupRepository.findById(id);
        return groupEntity.map(GroupMapper::entitytoDomain);
    }

}
