package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.outbound.GroupPortOut;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper.GroupMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.mapper.UserMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaGroupRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GroupPersistanceAdapter implements GroupPortOut {

    private final JpaGroupRepository jpaGroupRepository;
    private final JpaUserRepository jpaUserRepository;

    public GroupPersistanceAdapter(JpaGroupRepository jpaGroupRepository, JpaUserRepository jpaUserRepository) {
        this.jpaGroupRepository = jpaGroupRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Group save(Group group) {

        GroupEntity groupEntity = GroupMapper.domainToEntity(group);
        GroupEntity savedEntity = jpaGroupRepository.save(groupEntity);

        return GroupMapper.entityToDomain(savedEntity);
    }

    @Override
    public Optional<Group> findByName(String name) {
        Optional<GroupEntity> groupEntity = jpaGroupRepository.findByName(name);
        return groupEntity.map(GroupMapper::entityToDomain);
    }

    @Override
    public List<Group> findAll() {
        return jpaGroupRepository.findAll().stream()
                .map(GroupMapper::entityToDomain)
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
        groupEntity.ifPresent(entity -> entity.getMembers().size());
        return groupEntity.map(GroupMapper::entityToDomain);
    }

}
