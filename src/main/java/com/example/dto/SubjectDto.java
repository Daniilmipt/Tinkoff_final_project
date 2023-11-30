package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class SubjectDto {
    @JsonProperty("subject_id")
    private UUID id;

    @JsonProperty("subject_type_id")
    private UUID subjectTypeId;

    @JsonProperty("subject_nm")
    private String name;

}
