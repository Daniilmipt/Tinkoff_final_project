package com.example.models.keys;

import com.example.models.Subject;
import com.example.models.Travel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Setter
@Getter
@Embeddable
public class TravelAndSubjectId implements Serializable {
    @Column(name = "travel_id")
    private UUID travel = UUID.randomUUID();

    @Column(name = "subject_id")
    private UUID subject = UUID.randomUUID();

    @Column(name = "features")
    private String features;

    public TravelAndSubjectId(UUID travel, UUID subject) {
        this.travel = travel;
        this.subject = subject;
    }

    public TravelAndSubjectId(){}
}
