package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class RolesDto {
    @JsonProperty("role_id")
    private UUID id;

    @JsonProperty("role_nm")
    private String name;
}
