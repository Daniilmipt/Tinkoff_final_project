package com.example.security.controller;

import com.example.security.dto.AuthRequestDTO;
import com.example.security.util.SecurityRunner;
import com.example.services.RoleServiceImpl;
import com.example.services.UserAndRoleServiceImpl;
import com.example.services.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthenticationController {

    private final SecurityRunner securityRunner;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    RoleServiceImpl roleService,
                                    UserServiceImpl userService,
                                    UserAndRoleServiceImpl userAndRoleService) {
        this.securityRunner = new SecurityRunner(authenticationManager, userService, roleService, userAndRoleService);
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@Valid @RequestBody AuthRequestDTO authRequest,
                                        Model model) {
        securityRunner.setModel(model);
        securityRunner.authUser(authRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/out")
    public ResponseEntity<Object> logout(Model model) {
        securityRunner.setModel(model);
        securityRunner.logoutUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> addUser(@Valid @RequestBody AuthRequestDTO authenticationDto,
                                          Model model) {
        securityRunner.setModel(model);
        securityRunner.addUser(authenticationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
