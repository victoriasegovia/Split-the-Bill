package com.civica.splitthebill.domain.port.out;

import com.civica.splitthebill.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User user);
    Optional<User> findById(Long id);
    List<User> findAllById(List<Long> ids);

}
