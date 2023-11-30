package com.example.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.IdentifierGenerator;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @Column(name = "role_id")
    private UUID id = UUID.randomUUID();

    @Column(name = "role_nm")
    private String name;

    public Roles(String name){
        this.name = name;
    }

    public Roles(){}
}
