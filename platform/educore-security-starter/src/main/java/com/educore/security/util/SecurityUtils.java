package com.educore.security.util;

import com.educore.security.principal.CurrentUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 9:34
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public final class SecurityUtils {

    private static final String CLAIM_USERNAME =
            "preferred_username";

    private static final String CLAIM_EMAIL = "email";

    private static final String CLAIM_FULL_NAME = "name";

    private static final String CLAIM_GIVEN_NAME =
            "given_name";

    private static final String CLAIM_FAMILY_NAME =
            "family_name";

    private SecurityUtils() {
        throw new IllegalStateException(
                "Utility class must not be instantiated"
        );
    }

    /**
     * Lấy Authentication hiện tại.
     */
    public static Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );
    }

    /**
     * Kiểm tra request hiện tại đã được xác thực hay chưa.
     */
    public static boolean isAuthenticated() {
        return getAuthentication()
                .filter(Authentication::isAuthenticated)
                .filter(authentication ->
                        !(authentication
                                instanceof
                                AnonymousAuthenticationToken)
                )
                .isPresent();
    }

    /**
     * Lấy người dùng hiện tại dưới dạng Optional.
     */
    public static Optional<CurrentUser> getCurrentUser() {
        return getAuthentication()
                .filter(Authentication::isAuthenticated)
                .filter(authentication ->
                        !(authentication
                                instanceof
                                AnonymousAuthenticationToken)
                )
                .flatMap(SecurityUtils::mapCurrentUser);
    }

    /**
     * Bắt buộc phải có người dùng đã xác thực.
     */
    public static CurrentUser requireCurrentUser() {
        return getCurrentUser()
                .orElseThrow(() ->
                        new AuthenticationCredentialsNotFoundException(
                                "No authenticated user was found"
                        )
                );
    }

    public static Optional<String> getCurrentUserId() {
        return getCurrentUser()
                .map(CurrentUser::id);
    }

    public static String requireCurrentUserId() {
        return requireCurrentUser().id();
    }

    public static Optional<String> getCurrentUsername() {
        return getCurrentUser()
                .map(CurrentUser::username);
    }

    public static String requireCurrentUsername() {
        return requireCurrentUser().username();
    }

    public static boolean hasAuthority(String authority) {
        return getCurrentUser()
                .map(user ->
                        user.hasAuthority(authority)
                )
                .orElse(false);
    }

    public static boolean hasRole(String role) {
        return getCurrentUser()
                .map(user ->
                        user.hasRole(role)
                )
                .orElse(false);
    }

    /**
     * Lấy JWT hiện tại khi Authentication là JwtAuthenticationToken.
     */
    public static Optional<Jwt> getCurrentJwt() {
        return getAuthentication()
                .filter(JwtAuthenticationToken.class::isInstance)
                .map(JwtAuthenticationToken.class::cast)
                .map(JwtAuthenticationToken::getToken);
    }

    private static Optional<CurrentUser> mapCurrentUser(
            Authentication authentication
    ) {
        Jwt jwt = resolveJwt(authentication);

        if (jwt == null) {
            return Optional.empty();
        }

        String id = jwt.getSubject();

        String username = firstNonBlank(
                jwt.getClaimAsString(CLAIM_USERNAME),
                authentication.getName(),
                id
        );

        String email = jwt.getClaimAsString(CLAIM_EMAIL);

        String fullName = resolveFullName(jwt);

        Set<String> authorities =
                extractAuthorities(
                        authentication.getAuthorities()
                );

        return Optional.of(
                new CurrentUser(
                        id,
                        username,
                        email,
                        fullName,
                        authorities
                )
        );
    }

    private static Jwt resolveJwt(
            Authentication authentication
    ) {
        if (authentication instanceof JwtAuthenticationToken jwtToken) {
            return jwtToken.getToken();
        }

        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt;
        }

        return null;
    }

    private static Set<String> extractAuthorities(
            Collection<? extends GrantedAuthority>
                    grantedAuthorities
    ) {
        if (grantedAuthorities == null || grantedAuthorities.isEmpty()) {
            return Set.of();
        }

        Set<String> authorities = new LinkedHashSet<>();

        for (GrantedAuthority grantedAuthority : grantedAuthorities) {

            if (grantedAuthority == null) {
                continue;
            }

            String authority = grantedAuthority.getAuthority();

            if (authority == null || authority.isBlank()) {
                continue;
            }

            authorities.add(authority);
        }

        return Set.copyOf(authorities);
    }

    private static String resolveFullName(Jwt jwt) {
        String fullName = jwt.getClaimAsString(CLAIM_FULL_NAME);

        if (fullName != null && !fullName.isBlank()) {
            return fullName.trim();
        }

        String givenName = jwt.getClaimAsString(CLAIM_GIVEN_NAME);

        String familyName = jwt.getClaimAsString(CLAIM_FAMILY_NAME);

        String combinedName = String.join(
                " ",
                normalize(givenName),
                normalize(familyName)
        ).trim();

        return combinedName.isBlank()
                ? null
                : combinedName;
    }

    private static String firstNonBlank(
            String... values
    ) {
        if (values == null) {
            return null;
        }

        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value.trim();
            }
        }

        return null;
    }

    private static String normalize(String value) {
        return value == null
                ? ""
                : value.trim();
    }
}