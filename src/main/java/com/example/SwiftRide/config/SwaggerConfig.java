package com.example.SwiftRide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestion des Taxis SwiftRide")
                        .description("Documentation de l'API pour la gestion des véhicules et des courses")
                        .version("1.0"));
    }
} 