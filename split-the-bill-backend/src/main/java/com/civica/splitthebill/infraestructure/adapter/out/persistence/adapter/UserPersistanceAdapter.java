package com.civica.splitthebill.infraestructure.adapter.out.persistence.adapter;

import java.util.Optional;
import org.springframework.stereotype.Component;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.UserRepository;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.civica.splitthebill.infraestructure.adapter.out.persistence.repository.JpaUserRepository;

@Component
public class UserPersistanceAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserPersistanceAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.id());
        userEntity.setName(user.name());

        UserEntity savedEntity = jpaUserRepository.save(userEntity);
        return UserMapper.entitytoDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
