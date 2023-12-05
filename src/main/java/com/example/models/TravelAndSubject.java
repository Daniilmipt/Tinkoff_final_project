package com.example.models;

import com.example.models.keys.TravelAndSubjectId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "travel_x_subject")
public class TravelAndSubject {
    @EmbeddedId
    private TravelAndSubjectId travelAndSubjectId;

    @Column(name = "start_dttm")
    private LocalDateTime startDateTime;

    @Column(name = "end_dttm")
    private LocalDateTime endDateTime;

    @Column(name = "total_amt")
    private BigDecimal totalAmount;
}
