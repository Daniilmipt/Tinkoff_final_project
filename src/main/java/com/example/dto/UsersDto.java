package com.example.dto;

import com.example.models.Travel;
import com.example.models.UsersAndRoles;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class UsersDto implements Serializable {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("user_nm")
    private String name;

    @JsonProperty("password")
    private String password;

    private List<UsersAndRoles> usersAndRolesList;

    private List<Travel> travelList;
}
