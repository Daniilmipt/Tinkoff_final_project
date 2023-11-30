package com.example.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "user_id")
    private UUID id = UUID.randomUUID();

    @Column(name = "user_nm")
    private String name;

    @Column(name = "password")
    private String password;
}
