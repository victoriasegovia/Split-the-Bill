package com.civica.splitthebill.application.services;

import java.util.List;

import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.domain.port.in.UserService;

public class UserUseCases implements UserService {

    @Override
    public UserDTO createUserUseCase(UserDTO userDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUserUseCase'");
    }

    @Override
    public List<UserDTO> listUsersInGroupUseCase(Long groupId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listUsersInGroupUseCase'");
    }
    
}
