package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.port.out.GroupRepository;
import com.civica.splitthebill.infraestructure.adapter.in.rest.mapper.GroupDomainMapper;
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
        GroupEntity saved = jpaGroupRepository.save(GroupDomainMapper.toEntity(group));
        return GroupDomainMapper.toDomain(saved);
    }

    @Override
    public List<Group> findAll() {
        return jpaGroupRepository.findAll().stream()
                .map(GroupDomainMapper::toDomain)
                .toList();
    }
    
    @Override
    public Optional<Group> findById(Long id) {
        return jpaGroupRepository.findById(id).map(GroupDomainMapper::toDomain);
    }
    
    @Override
    public Optional<Group> findByName(String name) {
        return jpaGroupRepository.findByName(name).map(GroupDomainMapper::toDomain);
    }
    
}
