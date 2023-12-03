package com.example.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID id = UUID.randomUUID();

    @Column(name = "user_nm")
    private String name;

    @Column(name = "password")
    private String password;
}
