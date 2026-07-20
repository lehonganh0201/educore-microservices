package com.educore.security.converter;

import com.educore.security.properties.EducoreSecurityProperties;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 9:04
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public final class KeycloakGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final EducoreSecurityProperties properties;

    private final JwtGrantedAuthoritiesConverter scopeAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    public KeycloakGrantedAuthoritiesConverter(
            EducoreSecurityProperties properties
    ) {
        this.properties = properties;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();

        Collection<GrantedAuthority> scopeAuthorities = scopeAuthoritiesConverter.convert(jwt);

        if (scopeAuthorities != null) {
            authorities.addAll(scopeAuthorities);
        }

        if (properties.isIncludeRealmRoles()) {
            authorities.addAll(extractRealmRoles(jwt));
        }

        if (properties.isIncludeClientRoles()) {
            authorities.addAll(extractClientRoles(jwt));
        }

        return authorities;
    }

    private Collection<GrantedAuthority> extractRealmRoles(
            Jwt jwt
    ) {
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");

        if (realmAccess == null) {
            return List.of();
        }

        return mapRoles(realmAccess.get("roles"));
    }

    private Collection<GrantedAuthority> extractClientRoles(
            Jwt jwt
    ) {
        String clientId = properties.getClientId();

        if (clientId == null || clientId.isBlank()) {
            return List.of();
        }

        Map<String, Object> resourceAccess =
                jwt.getClaimAsMap("resource_access");

        if (resourceAccess == null) {
            return List.of();
        }

        Object clientAccessObject =
                resourceAccess.get(clientId);

        if (!(clientAccessObject instanceof Map<?, ?> clientAccess)) {
            return List.of();
        }

        return mapRoles(clientAccess.get("roles"));
    }

    private Collection<GrantedAuthority> mapRoles(
            Object rolesObject
    ) {
        if (!(rolesObject instanceof Collection<?> roles)) {
            return List.of();
        }

        return roles.stream()
                .map(String::valueOf)
                .filter(role -> !role.isBlank())
                .map(role ->
                        new SimpleGrantedAuthority(
                                properties.getAuthorityPrefix()
                                        + role
                        )
                )
                .map(GrantedAuthority.class::cast)
                .toList();
    }
}