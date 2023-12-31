package com.example.security.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncoderConfig {

    public static PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(8);
    }
}