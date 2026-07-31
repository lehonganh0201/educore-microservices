package com.educore.identifyservice.infrastructure.keycloak;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
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

    @Bean(destroyMethod = "close")
    public Keycloak keycloakAdminClient(
            KeycloakProperties properties
    ) {
        return KeycloakBuilder.builder()
                .serverUrl(properties.baseUrl())
                .realm(properties.realm())
                .grantType(
                        OAuth2Constants.CLIENT_CREDENTIALS
                )
                .clientId(properties.adminClientId())
                .clientSecret(properties.adminClientSecret())
                .build();
    }
}
