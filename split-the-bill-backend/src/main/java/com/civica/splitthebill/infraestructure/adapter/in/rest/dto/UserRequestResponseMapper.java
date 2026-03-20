package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.application.dto.UserDTO;

import java.util.List;

public final class UserRequestResponseMapper {
    
    private UserRequestResponseMapper() {}

    public static UserResponse domainDTOToResponse(UserDTO userDTO, List<ExpenseDTO> expenses) {
        return new UserResponse(
            userDTO.userId(),
            userDTO.name(),
            expenses
        );
    }

    public static UserDTO requestToDomainDTO(UserRequest userRequest) {
        return new UserDTO(
            userRequest.userId(),
            userRequest.name()
        );
    }
}
