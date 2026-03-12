package com.civica.splitthebill.application.mapper;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.domain.model.User;

public class UserDTOMapper {
    
    public UserDTOMapper() {
    }

    public static UserDTO domainToDTO(User user) {
        return new UserDTO(user.id(), user.name(), user.groupIds(), user.expenseIds());
    }

    public static User DTOToDomain(UserDTO userDTO) {
        return new User(userDTO.id(),  userDTO.name(), userDTO.groupIds(), userDTO.expenseIds());
    }
}
