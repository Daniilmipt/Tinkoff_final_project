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
@Table(name = "subject")
public class Subject {
    @Id
    @Column(name = "subject_id")
    private UUID id = UUID.randomUUID();

    @Column(name = "subject_type_id")
    private UUID subjectTypeId;

    @Column(name = "subject_nm")
    private String name;

    public Subject (String name){
        this.name=name;
    }
}
