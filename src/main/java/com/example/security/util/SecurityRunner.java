package com.example.security.util;

import com.example.dto.RolesDto;
import com.example.dto.UsersDto;
import com.example.exception.AuthException;
import com.example.models.Roles;
import com.example.models.Users;
import com.example.security.dto.AuthRequestDTO;
import com.example.services.RoleServiceImpl;
import com.example.services.UserAndRoleServiceImpl;
import com.example.services.UserServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.Optional;

public class SecurityRunner {
    private final AuthenticationManager authenticationManager;
    private Model model;
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final UserAndRoleServiceImpl userAndRoleService;

    public SecurityRunner(AuthenticationManager authenticationManager,
                          UserServiceImpl userService,
                          RoleServiceImpl roleService,
                          UserAndRoleServiceImpl userAndRoleService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.userAndRoleService = userAndRoleService;
    }

    public void setModel(Model model){
        this.model = model;
    }

    public void authUser(AuthRequestDTO authenticationDto){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (
                                authenticationDto.getName(),
                                authenticationDto.getPassword()
                        )
                );
        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<UsersDto> userDto = userService.findByName(authenticationDto.getName());
//        if (userDto.isEmpty()){
//            throw new AuthorizationException("Ошибка при попытки авторизации пользователя", "/auth");
//        }
        model.addAttribute("user", userDto.get());
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
        model.addAttribute("user", userDto);
    }

    public void logoutUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthException(
                    "The user is not logged in"
            );
        } else {
            SecurityContextHolder.clearContext();
            if (model != null) {
                model.addAttribute("user", null);
            }
        }
    }
}
