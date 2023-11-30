package com.example.models.keys;

import com.example.models.Roles;
import com.example.models.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@Embeddable
public class UsersAndRolesId implements Serializable {
    @Column(name = "user_id")
    private UUID user;

    @Column(name = "role_id")
    private UUID role;
}
