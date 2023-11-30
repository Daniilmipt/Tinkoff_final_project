package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class SubjectTypeDto {
    @JsonProperty("subject_type_id")
    private UUID id ;

    @JsonProperty("subject_type_nm")
    private String name;
}
