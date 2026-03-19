package com.civica.splitthebill.domain.port.out;

import com.civica.splitthebill.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserPortOut {

    Optional<User> save(User user);
    Optional<User> findById(Long userId);
    List<User> findAllByGroupId(Long groupId);

}