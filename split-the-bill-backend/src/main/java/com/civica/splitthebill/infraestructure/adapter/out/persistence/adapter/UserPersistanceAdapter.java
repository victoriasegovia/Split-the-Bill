package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import java.util.Optional;
import org.springframework.stereotype.Component;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.UserRepository;
import com.civica.splitthebill.infraestructure.adapter.in.rest.mapper.UserMapper;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaUserRepository;

@Component
public class UserPersistanceAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserPersistanceAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> save(User user) {
        UserEntity userEntity = new UserEntity(user.id(), user.name());
        jpaUserRepository.save(userEntity);

        return Optional.of(UserMapper.entitytoDomain(userEntity));
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserEntity> userEntity = jpaUserRepository.findById(id);
        return userEntity.map(UserMapper::entitytoDomain);
    }

    
}
