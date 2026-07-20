package com.educore.identifyservice.infrastructure.keycloak;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 16:15
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Configuration
@EnableConfigurationProperties(KeycloakProperties.class)
public class KeycloakRestClientConfiguration {

    @Bean
    public RestClient keycloakRestClient(
            RestClient.Builder builder,
            KeycloakProperties properties
    ) {
        return builder
                .baseUrl(properties.baseUrl())
                .build();
    }
}
