package com.civica.splitthebill.domain.port.out;

import com.civica.splitthebill.domain.model.User;
import java.util.Optional;

public interface UserRepository {

    User save(User group);
    Optional<User> findById(Long id);

}
