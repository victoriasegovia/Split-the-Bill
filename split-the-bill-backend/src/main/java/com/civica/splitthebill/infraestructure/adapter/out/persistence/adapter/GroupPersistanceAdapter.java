package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.GroupRepository;
import com.civica.splitthebill.infraestructure.adapter.in.rest.mapper.GroupMapper;
import com.civica.splitthebill.infraestructure.adapter.in.rest.mapper.UserMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaGroupRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GroupPersistanceAdapter implements GroupRepository {

    private final JpaGroupRepository jpaGroupRepository;
    private final JpaUserRepository jpaUserRepository;

    public GroupPersistanceAdapter(JpaGroupRepository jpaGroupRepository, JpaUserRepository jpaUserRepository) {
        this.jpaGroupRepository = jpaGroupRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<Group> save(Group group) {
        GroupEntity groupEntity = jpaGroupRepository.findById(group.id())
                .orElseGet(GroupEntity::new);

        groupEntity.setId(group.id());
        groupEntity.setName(group.name());

        List<UserEntity> memberEntities = jpaUserRepository.findAllById(group.memberIds());

        groupEntity.getMembers().clear();
        groupEntity.getMembers().addAll(memberEntities);

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
        List<UserEntity> users = jpaUserRepository.findByGroups_Id(groupId);

        return users.stream()
                .map(UserMapper::entityToDomain)
                .toList();
    }

    @Override
    public Optional<Group> findById(Long id) {
        Objects.requireNonNull("Id cannot be null");
        Optional<GroupEntity> groupEntity = jpaGroupRepository.findById(id);
        return groupEntity.map(GroupMapper::entitytoDomain);
    }

}
