package com.educore.security.handler;

import com.educore.common.dto.ApiError;
import com.educore.common.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 9:26
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String ERROR_CODE = "UNAUTHORIZED";

    private static final String ERROR_MESSAGE =
            "Authentication is required to access this resource";

    private final ObjectMapper objectMapper;

    private static final Logger log =
            LoggerFactory.getLogger(
                    RestAuthenticationEntryPoint.class
            );

    /*
     * Delegate này giúp giữ hành vi chuẩn của OAuth2 Resource Server,
     * đặc biệt là header WWW-Authenticate.
     */
    private final BearerTokenAuthenticationEntryPoint delegate =
            new BearerTokenAuthenticationEntryPoint();

    public RestAuthenticationEntryPoint(
            ObjectMapper objectMapper
    ) {
        this.objectMapper = Objects.requireNonNull(
                objectMapper,
                "ObjectMapper must not be null"
        );
    }

    @Override
    public void commence(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull AuthenticationException authenticationException
    ) throws IOException, ServletException {

        if (response.isCommitted()) {
            return;
        }

        /*
         * Thiết lập status và WWW-Authenticate theo chuẩn Bearer Token.
         */
        delegate.commence(
                request,
                response,
                authenticationException
        );

        /*
         * Xóa phần body đang được buffer nếu có,
         * nhưng vẫn giữ status và các response header.
         */
        response.resetBuffer();

        ApiResponse<Void> responseBody =
                ApiResponse.error(
                        ERROR_MESSAGE,
                        ApiError.of(ERROR_CODE)
                );

        log.warn(
                "Authentication failed path={} reason={}",
                request.getRequestURI(),
                authenticationException.getClass()
                        .getSimpleName()
        );

        writeJsonResponse(response, responseBody);
    }

    private void writeJsonResponse(
            HttpServletResponse response,
            ApiResponse<Void> responseBody
    ) throws IOException {

        /*
         * Delegate thông thường đã đặt 401.
         * Thiết lập lại giúp bảo đảm response luôn chính xác.
         */
        response.setStatus(
                HttpServletResponse.SC_UNAUTHORIZED
        );

        response.setContentType(
                MediaType.APPLICATION_JSON_VALUE
        );

        response.setCharacterEncoding(
                StandardCharsets.UTF_8.name()
        );

        /*
         * Không cho proxy hoặc browser cache response bảo mật.
         */
        response.setHeader(
                HttpHeaders.CACHE_CONTROL,
                "no-store"
        );

        response.setHeader(
                HttpHeaders.PRAGMA,
                "no-cache"
        );

        objectMapper.writeValue(
                response.getOutputStream(),
                responseBody
        );

        response.flushBuffer();
    }
}
