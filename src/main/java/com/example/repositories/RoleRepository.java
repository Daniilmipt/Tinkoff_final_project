package com.example.repositories;

import com.example.models.Roles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Roles, UUID> {
    Optional<Roles> findRolesByName(String name);
}
