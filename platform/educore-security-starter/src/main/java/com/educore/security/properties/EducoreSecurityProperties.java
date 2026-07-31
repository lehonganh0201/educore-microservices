package com.educore.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 9:01
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@ConfigurationProperties(prefix = "edu.security")
public class EducoreSecurityProperties {

    /**
     * Bật hoặc tắt security auto-configuration.
     */
    private boolean enabled = true;

    /**
     * Keycloak client ID của microservice hiện tại.
     */
    private String clientId;

    /**
     * Claim dùng làm tên principal.
     */
    private String principalClaim = "preferred_username";

    /**
     * Prefix thêm vào role.
     */
    private String authorityPrefix = "ROLE_";

    /**
     * Có lấy realm role hay không.
     */
    private boolean includeRealmRoles = true;

    /**
     * Có lấy client role hay không.
     */
    private boolean includeClientRoles = true;

    /**
     * Những endpoint không yêu cầu xác thực.
     */
    private final List<String> publicPaths = new ArrayList<>(
            List.of(
                    "/actuator/health/**",
                    "/actuator/info",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
            )
    );

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPrincipalClaim() {
        return principalClaim;
    }

    public void setPrincipalClaim(String principalClaim) {
        this.principalClaim = principalClaim;
    }

    public String getAuthorityPrefix() {
        return authorityPrefix;
    }

    public void setAuthorityPrefix(String authorityPrefix) {
        this.authorityPrefix = authorityPrefix;
    }

    public boolean isIncludeRealmRoles() {
        return includeRealmRoles;
    }

    public void setIncludeRealmRoles(boolean includeRealmRoles) {
        this.includeRealmRoles = includeRealmRoles;
    }

    public boolean isIncludeClientRoles() {
        return includeClientRoles;
    }

    public void setIncludeClientRoles(boolean includeClientRoles) {
        this.includeClientRoles = includeClientRoles;
    }

    public List<String> getPublicPaths() {
        return publicPaths;
    }
}