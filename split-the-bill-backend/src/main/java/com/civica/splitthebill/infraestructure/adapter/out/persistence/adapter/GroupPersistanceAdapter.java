package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.GroupRepository;
import com.civica.splitthebill.infraestructure.adapter.in.rest.mapper.GroupMapper;
import com.civica.splitthebill.infraestructure.adapter.in.rest.mapper.UserMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaGroupRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaUserRepository;

@Component
public class GroupPersistanceAdapter implements GroupRepository {

    private final JpaGroupRepository jpaGroupRepository;
    private final JpaUserRepository jpaUserRepository;

    public GroupPersistanceAdapter(JpaGroupRepository jpaGroupRepository, JpaUserRepository jpaUserRepository) {
        this.jpaGroupRepository = jpaGroupRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<Group> save(Group group) {
        GroupEntity groupEntity = GroupMapper.domaintoEntity(group);
        GroupEntity savedEntity = jpaGroupRepository.save(groupEntity);
        return Optional.of(GroupMapper.entitytoDomain(savedEntity));
    }

    @Override
    public Optional<Group> findByName(String name) {
        Optional <GroupEntity> groupEntity =  jpaGroupRepository.findByName(name);
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
        GroupEntity groupEntity = jpaGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));

        if (groupEntity.getMembers() == null) {
            throw new IllegalArgumentException("Group with id: " + groupId + " has no members.");
        }

        return groupEntity.getMembers().stream()
                .map(UserMapper::entitytoDomain)
                .toList();
    }

    @Override
    public void addUserToGroup(Long groupId, Long userId) {
        UserEntity userEntity = jpaUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        GroupEntity groupEntity = jpaGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));
        groupEntity.getMembers().add(userEntity);
        jpaGroupRepository.save(groupEntity);
    }

    @Override
    public void removeUserFromGroup(Long groupId, Long userId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
