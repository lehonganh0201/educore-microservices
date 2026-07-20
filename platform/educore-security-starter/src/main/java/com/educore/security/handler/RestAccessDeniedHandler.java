package com.educore.security.handler;

import com.educore.common.dto.ApiError;
import com.educore.common.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 9:28
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public final class RestAccessDeniedHandler implements AccessDeniedHandler {

    private static final String ERROR_CODE = "FORBIDDEN";

    private static final String ERROR_MESSAGE =
            "You do not have permission to access this resource";

    private final ObjectMapper objectMapper;

    private final BearerTokenAccessDeniedHandler delegate =
            new BearerTokenAccessDeniedHandler();

    public RestAccessDeniedHandler(
            ObjectMapper objectMapper
    ) {
        this.objectMapper = Objects.requireNonNull(
                objectMapper,
                "ObjectMapper must not be null"
        );
    }

    @Override
    public void handle(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {

        if (response.isCommitted()) {
            return;
        }

        /*
         * Giữ header WWW-Authenticate theo RFC 6750.
         */
        delegate.handle(
                request,
                response,
                accessDeniedException
        );

        response.resetBuffer();

        ApiResponse<Void> responseBody =
                ApiResponse.error(
                        ERROR_MESSAGE,
                        ApiError.of(ERROR_CODE)
                );

        writeJsonResponse(response, responseBody);
    }

    private void writeJsonResponse(
            HttpServletResponse response,
            ApiResponse<Void> responseBody
    ) throws IOException {

        response.setStatus(
                HttpServletResponse.SC_FORBIDDEN
        );

        response.setContentType(
                MediaType.APPLICATION_JSON_VALUE
        );

        response.setCharacterEncoding(
                StandardCharsets.UTF_8.name()
        );

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