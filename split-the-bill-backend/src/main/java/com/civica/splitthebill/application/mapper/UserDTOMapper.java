package com.civica.splitthebill.application.mapper;

import org.springframework.stereotype.Component;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.domain.model.User;

@Component
public class UserDTOMapper {

    public UserDTO domainToDTO(User user) {
        return new UserDTO(user.userId(), user.name());
    }
}
