package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConfig {

    @Bean("WebClientApi")
    public WebClient.Builder webClientAviasalesBuilder() {
        return WebClient.builder();
    }
}