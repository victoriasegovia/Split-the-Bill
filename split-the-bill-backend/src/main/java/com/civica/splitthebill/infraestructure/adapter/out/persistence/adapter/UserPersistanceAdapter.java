package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.GroupLayout.Group;

import org.springframework.stereotype.Component;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.UserRepository;
import com.civica.splitthebill.infraestructure.adapter.in.rest.mapper.UserMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.GroupEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaGroupRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaUserRepository;

@Component
public class UserPersistanceAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final JpaGroupRepository jpaGroupRepository;

    public UserPersistanceAdapter(JpaUserRepository jpaUserRepository, JpaGroupRepository jpaGroupRepository) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaGroupRepository = jpaGroupRepository;
    }

    @Override
    public Optional<User> save(User user) {
        UserEntity userEntity = new UserEntity(user.id(), user.name(), user.groupIds(), user.expenseIds());
        jpaUserRepository.save(userEntity);

        return Optional.of(UserMapper.entitytoDomain(userEntity));
    }

    @Override
    public Optional<User> findById(Long userId) {
        Optional<UserEntity> userEntity = jpaUserRepository.findById(userId);
        return userEntity.map(UserMapper::entitytoDomain);
    }

    @Override
    public List<User> findAllByGroupId(Long groupId) {
        List<UserEntity> userEntities = jpaUserRepository.findAllByGroupId(groupId);
        return userEntities.stream()
                .map(UserMapper::entitytoDomain)
                .collect(Collectors.toList());
    }
    
}
