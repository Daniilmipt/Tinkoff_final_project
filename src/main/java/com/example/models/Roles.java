package com.example.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
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
}
