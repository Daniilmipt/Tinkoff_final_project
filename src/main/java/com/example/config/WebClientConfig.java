package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConfig {

    @Bean("WebClientApi")
    public WebClient.Builder webClientAviasalesBuilder() {
        return WebClient.builder();
    }
}