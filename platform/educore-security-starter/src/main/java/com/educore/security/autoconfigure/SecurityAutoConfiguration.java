package com.educore.security.autoconfigure;

import com.educore.security.converter.KeycloakGrantedAuthoritiesConverter;
import com.educore.security.converter.KeycloakJwtAuthenticationConverter;
import com.educore.security.handler.RestAccessDeniedHandler;
import com.educore.security.handler.RestAuthenticationEntryPoint;
import com.educore.security.properties.EducoreSecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 9:08
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@AutoConfiguration
@ConditionalOnClass({
        HttpSecurity.class,
        Jwt.class
})
@ConditionalOnWebApplication(
        type = ConditionalOnWebApplication.Type.SERVLET
)
@ConditionalOnProperty(
        prefix = "edu.security",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@EnableConfigurationProperties(
        EducoreSecurityProperties.class
)
public class SecurityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public KeycloakGrantedAuthoritiesConverter keycloakGrantedAuthoritiesConverter(
            EducoreSecurityProperties properties
    ) {
        return new KeycloakGrantedAuthoritiesConverter(
                properties
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public KeycloakJwtAuthenticationConverter keycloakJwtAuthenticationConverter(
            KeycloakGrantedAuthoritiesConverter converter,
            EducoreSecurityProperties properties
    ) {
        return new KeycloakJwtAuthenticationConverter(
                converter,
                properties
        );
    }

    @Bean
    @ConditionalOnMissingBean(
            RestAuthenticationEntryPoint.class
    )
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint(
            ObjectMapper objectMapper
    ) {
        return new RestAuthenticationEntryPoint(
                objectMapper
        );
    }

    @Bean
    @ConditionalOnMissingBean(
            RestAccessDeniedHandler.class
    )
    public RestAccessDeniedHandler restAccessDeniedHandler(
            ObjectMapper objectMapper
    ) {
        return new RestAccessDeniedHandler(
                objectMapper
        );
    }

    @Bean
    @ConditionalOnMissingBean(SecurityFilterChain.class)
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            EducoreSecurityProperties properties,
            KeycloakJwtAuthenticationConverter converter,
            RestAuthenticationEntryPoint entryPoint,
            RestAccessDeniedHandler accessDeniedHandler
    ) throws Exception {

        String[] publicPaths =
                properties.getPublicPaths()
                        .toArray(String[]::new);

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(publicPaths)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )

                .oauth2ResourceServer(oauth2 ->
                        oauth2
                                .jwt(jwt ->
                                        jwt.jwtAuthenticationConverter(
                                                converter
                                        )
                                )
                                .authenticationEntryPoint(entryPoint)
                                .accessDeniedHandler(
                                        accessDeniedHandler
                                )
                );

        return http.build();
    }
}
