package com.civica.splitthebill.domain.port.out;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.domain.model.User;
import java.util.Optional;

public interface UserRepository {

    Optional<User> save(UserDTO userDTO);
    Optional<User> findById(Long id);

}
