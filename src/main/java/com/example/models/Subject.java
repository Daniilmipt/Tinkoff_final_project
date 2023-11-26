package com.example.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @Column(name = "subject_id")
    private UUID id = UUID.randomUUID();;

    @Column(name = "subject_type_id")
    private UUID subjectTypeId;

    @Column(name = "subject_nm")
    private String name;
}
