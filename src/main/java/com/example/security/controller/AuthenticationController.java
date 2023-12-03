package com.example.security.controller;

import com.example.security.dto.AuthRequestDTO;
import com.example.security.util.SecurityRunner;
import com.example.services.RoleService;
import com.example.services.UserAndRoleService;
import com.example.services.UserService;
import com.sun.net.httpserver.Authenticator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthenticationController {

    private final SecurityRunner securityRunner;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    RoleService roleService,
                                    UserService userService,
                                    UserAndRoleService userAndRoleService) {
        this.securityRunner = new SecurityRunner(authenticationManager, userService, roleService, userAndRoleService);
    }

    @PostMapping("/auth")
    public ResponseEntity<Authenticator.Success> auth(@Valid @RequestBody AuthRequestDTO authRequest) {
        securityRunner.authUser(authRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/out")
    public ResponseEntity<Authenticator.Success> logout() {
        securityRunner.logoutUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<Authenticator.Success> addUser(@Valid @RequestBody AuthRequestDTO authenticationDto) {
        securityRunner.addUser(authenticationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
