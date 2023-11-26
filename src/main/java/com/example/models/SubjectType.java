package com.example.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Setter
@Getter
@Entity
@Table(name = "subject_type")
public class SubjectType {
    @Id
    @Column(name = "subject_type_id")
    private UUID id = UUID.randomUUID();

    @Column(name = "subject_type_nm")
    private String name;
}
