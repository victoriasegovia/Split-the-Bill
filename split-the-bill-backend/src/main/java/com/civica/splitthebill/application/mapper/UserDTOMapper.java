package com.civica.splitthebill.application.mapper;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.domain.model.User;

public class UserDTOMapper {
    
    public UserDTOMapper() {
    }

    public static UserDTO domaintoDTO(User user) {
        return new UserDTO(user.id(), user.name());
    }

    public static User DTOtoDomain(UserDTO userDTO) {
        return new User(userDTO.id(), userDTO.name());
    }
}
