package com.example.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "travel")
public class Travel {
    @Id
    @Column(name = "travel_id")
    private UUID travelId = UUID.randomUUID();

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "total_amt")
    private BigDecimal totalAmount;

    @Column(name = "start_dt")
    private LocalDateTime startDateTime;

    @Column(name = "end_dt")
    private LocalDateTime endDateTime;

    @Column(name = "deleted_flg")
    private Integer deleted;
}
