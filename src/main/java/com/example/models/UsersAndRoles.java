package com.example.models;

import com.example.models.keys.TravelAndSubjectId;
import com.example.models.keys.UsersAndRolesId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "users_x_roles")
public class UsersAndRoles {
    @EmbeddedId
    private UsersAndRolesId usersAndRolesId;
}
