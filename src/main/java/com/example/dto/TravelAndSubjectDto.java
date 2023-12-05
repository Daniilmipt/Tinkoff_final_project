package com.example.dto;

import com.example.SubjectTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TravelAndSubjectDto {

    @JsonProperty("content")
    private SubjectTypeEnum subjectTypeEnum;

    @JsonProperty("start_dttm")
    private LocalDateTime startDateTime;

    @JsonProperty("end_dttm")
    private LocalDateTime endDateTime;

    @JsonProperty("total_amt")
    private BigDecimal totalAmount;

    @JsonProperty("features")
    private String features;
}
