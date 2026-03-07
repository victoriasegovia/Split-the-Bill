package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.port.out.GroupRepository;
import com.civica.splitthebill.infraestructure.adapter.in.rest.mapper.GroupMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaGroupRepository;

@Component
public class GroupPersistanceAdapter implements GroupRepository {
    
    private final JpaGroupRepository jpaGroupRepository;

    public GroupPersistanceAdapter(JpaGroupRepository jpaGroupRepository) {
        this.jpaGroupRepository = jpaGroupRepository;
    }

    @Override
    public Group save(Group group) {
        GroupEntity saved = jpaGroupRepository.save(GroupMapper.domaintoEntity(group));
        return GroupMapper.entitytoDomain(saved);
    }

    @Override
    public Optional<Group> findByName(String name) {
        Optional <GroupEntity> group = jpaGroupRepository.findByName(name);
        return group.map(GroupMapper::entitytoDomain);
    }

    @Override
    public List<Group> findAll() {
        return jpaGroupRepository.findAll().stream()
                .map(GroupMapper::entitytoDomain)
                .toList();
    }
    
}
