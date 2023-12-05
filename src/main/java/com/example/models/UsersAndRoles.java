package com.example.models;

import com.example.models.keys.UsersAndRolesId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "users_x_roles")
public class UsersAndRoles {
    @EmbeddedId
    private UsersAndRolesId usersAndRolesId;
}
