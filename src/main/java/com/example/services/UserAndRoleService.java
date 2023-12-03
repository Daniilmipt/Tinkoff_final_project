package com.example.services;

import com.example.repositories.UserAndRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserAndRoleService {
    private final UserAndRoleRepository userAndRoleRepository;

    public UserAndRoleService(UserAndRoleRepository userAndRoleRepository) {
        this.userAndRoleRepository = userAndRoleRepository;
    }

    @Transactional
    public void save(UUID userId, UUID roleId){
        userAndRoleRepository.saveUserAndRole(userId, roleId);
    }
}
