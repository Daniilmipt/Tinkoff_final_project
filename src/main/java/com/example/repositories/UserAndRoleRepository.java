package com.example.repositories;

import com.example.models.Roles;
import com.example.models.UsersAndRoles;
import com.example.models.keys.UsersAndRolesId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAndRoleRepository extends CrudRepository<UsersAndRoles, UsersAndRolesId> {
    @Modifying
    @Query(value = "insert into users_x_roles(user_id, role_id) values (?1, ?2);", nativeQuery = true)
    void saveUserAndRole(UUID userId, UUID roleId);
}
