package com.example.repositories;

import com.example.models.Roles;
import com.example.models.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<Users, UUID> {
    Optional<Users> findUsersByName(String userName);

    @Query("select r from UsersAndRoles uar " +
            "inner join Users u on uar.usersAndRolesId.user = u.id " +
            "inner join Roles r on uar.usersAndRolesId.role = r.id " +
            "where u.name = :name")
    List<Roles> getRolesByUserName(String name);
}
