package com.educore.logging.filter;

import com.educore.logging.generator.RequestIdGenerator;
import com.educore.logging.properties.EducoreLoggingProperties;
import com.educore.logging.util.LogValueSanitizer;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.server.PathContainer;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.educore.logging.constant.LoggingConstants.MDC_REQUEST_ID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 10:15
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public final class HttpRequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log =
            LoggerFactory.getLogger(
                    HttpRequestLoggingFilter.class
            );

    private static final Pattern SAFE_REQUEST_ID_PATTERN =
            Pattern.compile("[A-Za-z0-9._:-]+");

    private final EducoreLoggingProperties properties;

    private final RequestIdGenerator requestIdGenerator;

    private final List<PathPattern> excludedPathPatterns;

    public HttpRequestLoggingFilter(
            EducoreLoggingProperties properties,
            RequestIdGenerator requestIdGenerator
    ) {
        this.properties = properties;
        this.requestIdGenerator = requestIdGenerator;

        PathPatternParser parser =
                PathPatternParser.defaultInstance;

        this.excludedPathPatterns =
                properties.getExcludedPaths()
                        .stream()
                        .map(parser::parse)
                        .toList();
    }

    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request
    ) {
        String requestPath = request.getRequestURI();

        PathContainer pathContainer =
                PathContainer.parsePath(requestPath);

        return excludedPathPatterns.stream()
                .anyMatch(pattern ->
                        pattern.matches(pathContainer)
                );
    }

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        long startedAt = System.nanoTime();

        String requestId = resolveRequestId(request);

        String previousRequestId = MDC.get(MDC_REQUEST_ID);

        Throwable failure = null;

        MDC.put(MDC_REQUEST_ID, requestId);

        response.setHeader(
                properties.getRequestIdHeader(),
                requestId
        );

        try {
            filterChain.doFilter(request, response);
        } catch (
                IOException
                | ServletException
                | RuntimeException
                | Error exception
        ) {
            failure = exception;
            throw exception;
        } finally {
            try {
                if (properties.isAccessLogEnabled()) {
                    writeAccessLog(
                            request,
                            response,
                            failure,
                            startedAt
                    );
                }
            } finally {
                restoreMdc(previousRequestId);
            }
        }
    }

    private String resolveRequestId(
            HttpServletRequest request
    ) {
        String requestId = request.getHeader(
                properties.getRequestIdHeader()
        );

        if (!isValidRequestId(requestId)) {
            return requestIdGenerator.generate();
        }

        return requestId;
    }

    private boolean isValidRequestId(String requestId) {
        if (requestId == null || requestId.isBlank()) {
            return false;
        }

        if (requestId.length()
                > properties.getMaxRequestIdLength()) {
            return false;
        }

        return SAFE_REQUEST_ID_PATTERN
                .matcher(requestId)
                .matches();
    }

    private void writeAccessLog(
            HttpServletRequest request,
            HttpServletResponse response,
            Throwable failure,
            long startedAt
    ) {
        long durationMillis =
                TimeUnit.NANOSECONDS.toMillis(
                        System.nanoTime() - startedAt
                );

        int responseStatus = response.getStatus();

        if (failure != null && responseStatus < 400) {
            responseStatus =
                    HttpServletResponse
                            .SC_INTERNAL_SERVER_ERROR;
        }

        String method = LogValueSanitizer.sanitize(
                        request.getMethod()
                );

        String path = resolveRequestPath(request);

        String exceptionName =
                failure == null
                        ? null
                        : failure.getClass()
                        .getSimpleName();

        String message = String.format(
                "HTTP request completed method=%s path=%s status=%d durationMs=%d exception=%s",
                method,
                path,
                responseStatus,
                durationMillis,
                exceptionName
        );

        if (failure != null || responseStatus >= 500) {
            log.error(message);
            return;
        }

        if (responseStatus >= 400
                || isSlowRequest(durationMillis)) {
            log.warn(message);
            return;
        }

        log.info(message);
    }

    private String resolveRequestPath(
            HttpServletRequest request
    ) {
        String path = request.getRequestURI();

        if (properties.isIncludeQueryString()) {
            String queryString =
                    request.getQueryString();

            if (queryString != null
                    && !queryString.isBlank()) {
                path = path + "?" + queryString;
            }
        }

        return LogValueSanitizer.sanitize(path);
    }

    private boolean isSlowRequest(
            long durationMillis
    ) {
        return durationMillis
                >= properties
                .getSlowRequestThreshold()
                .toMillis();
    }

    private void restoreMdc(
            String previousRequestId
    ) {
        if (previousRequestId == null) {
            MDC.remove(MDC_REQUEST_ID);
        } else {
            MDC.put(
                    MDC_REQUEST_ID,
                    previousRequestId
            );
        }
    }
}
