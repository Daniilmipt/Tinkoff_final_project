package com.example.security.util;

import com.example.dto.RolesDto;
import com.example.dto.UsersDto;
import com.example.exception.AuthException;
import com.example.models.Roles;
import com.example.models.Users;
import com.example.security.dto.AuthRequestDTO;
import com.example.services.RoleService;
import com.example.services.UserAndRoleService;
import com.example.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityRunner {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final UserAndRoleService userAndRoleService;

    public SecurityRunner(AuthenticationManager authenticationManager,
                          UserService userService,
                          RoleService roleService,
                          UserAndRoleService userAndRoleService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.userAndRoleService = userAndRoleService;
    }

    public void authUser(AuthRequestDTO authenticationDto){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (
                                authenticationDto.getName(),
                                authenticationDto.getPassword()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<UsersDto> userDto = userService.findByName(authenticationDto.getName());
        if (userDto.isEmpty()){
            throw new AuthException("Ошибка при попытки авторизации пользователя");
        }
    }

    public void addUser(AuthRequestDTO authenticationDto){
        Users user = new Users();
        user.setName(authenticationDto.getName());
        user.setPassword(authenticationDto.getPassword());

        UsersDto userDto = userService.save(user);
        RolesDto role = roleService.save(new Roles("USER"));
        userAndRoleService.save(userDto.getId(), role.getId());

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (
                                authenticationDto.getName(),
                                authenticationDto.getPassword()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void logoutUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthException(
                    "The user is not logged in"
            );
        } else {
            SecurityContextHolder.clearContext();
        }
    }
}
