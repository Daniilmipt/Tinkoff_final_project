package com.example.services;

import com.example.models.UsersAndRoles;
import com.example.repositories.UserAndRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserAndRoleServiceImpl {
    private final UserAndRoleRepository userAndRoleRepository;

    public UserAndRoleServiceImpl(UserAndRoleRepository userAndRoleRepository) {
        this.userAndRoleRepository = userAndRoleRepository;
    }

    @Transactional
    public void save(UUID userId, UUID roleId){
        userAndRoleRepository.saveUserAndRole(userId, roleId);
    }
}
