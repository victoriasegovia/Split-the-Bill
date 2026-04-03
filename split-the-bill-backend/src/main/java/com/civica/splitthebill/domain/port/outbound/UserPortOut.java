package com.civica.splitthebill.domain.port.outbound;

import com.civica.splitthebill.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserPortOut {

    User save(User user);
    Optional<User> findById(Long userId);
    Optional<User> findByIdInGroup(Long groupId, Long userId);
    List<User> findAllByGroupId(Long groupId);

}