package com.civica.splitthebill.infraestructure.adapter.in.rest.dto;

import com.civica.splitthebill.application.dto.UserDTO;

import java.util.List;

public final class UserRequestResponseMapper {
    
    private UserRequestResponseMapper() {}

    public static UserResponse domainDTOToResponse(UserDTO userDTO) {
        return new UserResponse(
            userDTO.userId(),
            userDTO.name()
        );
    }

    public static UserDTO requestToDomainDTO(UserRequest userRequest) {
        return new UserDTO(
            userRequest.userId(),
            userRequest.name()
        );
    }
}
