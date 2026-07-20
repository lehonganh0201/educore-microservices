package com.educore.logging.constant;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 10:12
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public final class LoggingConstants {

    public static final String DEFAULT_REQUEST_ID_HEADER =
            "X-Request-Id";

    public static final String MDC_REQUEST_ID =
            "requestId";

    public static final String UNKNOWN_VALUE =
            "unknown";

    private LoggingConstants() {
        throw new IllegalStateException(
                "Constants class must not be instantiated"
        );
    }
}
