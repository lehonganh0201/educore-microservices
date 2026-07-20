package com.educore.identifyservice.infrastructure.keycloak;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 16:13
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@ConfigurationProperties(prefix = "edu.keycloak")
public record KeycloakProperties(
        String baseUrl,
        String realm,
        String clientId,
        String clientSecret
) {

    public String tokenPath() {
        return "/realms/%s/protocol/openid-connect/token"
                .formatted(realm);
    }
}
