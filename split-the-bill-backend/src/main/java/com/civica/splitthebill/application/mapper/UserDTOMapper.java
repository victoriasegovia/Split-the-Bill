package com.civica.splitthebill.application.mapper;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.domain.model.User;

public class UserDTOMapper {
    
    public UserDTOMapper() {
    }

    public static UserDTO domaintoDTO(User user) {
        return new UserDTO(user.id(), user.name(), user.groupIds(), user.expenseIds(), user.debtIds());
    }

    public static User DTOtoDomain(UserDTO userDTO) {
        return new User(userDTO.id(),  userDTO.name(), userDTO.groupIds(), userDTO.expenseIds(), userDTO.debtIds());
    }
}
