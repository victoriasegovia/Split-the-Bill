package com.civica.splitthebill.application.mapper;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.domain.model.User;

public class UserDTOMapper {

    public UserDTO domainToDTO(User user) {
        return new UserDTO(user.userId(), user.name());
    }
}
