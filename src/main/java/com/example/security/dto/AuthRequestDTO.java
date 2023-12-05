package com.example.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthRequestDTO {

    @NotBlank(message = "Login doesn't empty!")
    private String name;

    @NotBlank(message = "Password doesn't empty!")
    private String password;

}