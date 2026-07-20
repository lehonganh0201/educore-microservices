package com.educore.security.principal;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 9:30
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record CurrentUser(
        String id,
        String username,
        String email,
        String fullName,
        Set<String> authorities
) {

    private static final String ROLE_PREFIX = "ROLE_";

    public CurrentUser {
        authorities = authorities == null
                ? Set.of()
                : Set.copyOf(
                new LinkedHashSet<>(authorities)
        );
    }

    public boolean hasAuthority(String authority) {
        if (authority == null || authority.isBlank()) {
            return false;
        }

        return authorities.contains(authority.trim());
    }

    public boolean hasRole(String role) {
        if (role == null || role.isBlank()) {
            return false;
        }

        String normalizedRole = role.startsWith(ROLE_PREFIX)
                ? role
                : ROLE_PREFIX + role;

        return hasAuthority(normalizedRole);
    }

    public Set<String> roles() {
        return authorities.stream()
                .filter(authority ->
                        authority.startsWith(ROLE_PREFIX)
                )
                .map(authority ->
                        authority.substring(
                                ROLE_PREFIX.length()
                        )
                )
                .collect(
                        java.util.stream.Collectors
                                .toUnmodifiableSet()
                );
    }
}