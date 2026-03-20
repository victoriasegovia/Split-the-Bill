package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.civica.splitthebill.domain.port.in.UserPortIn;

@RestController
@RequestMapping("/api/users")
public class UserController {
 
    private final UserPortIn userPortIn;

    public UserController (UserPortIn userPortIn) {
        this.userPortIn = userPortIn;
    }
        
}
