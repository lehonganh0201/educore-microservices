package com.educore.common.constant;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 11:00
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public final class WebErrorCodes {

    public static final String BAD_REQUEST =
            "BAD_REQUEST";

    public static final String VALIDATION_FAILED =
            "VALIDATION_FAILED";

    public static final String MALFORMED_REQUEST =
            "MALFORMED_REQUEST";

    public static final String MISSING_PARAMETER =
            "MISSING_PARAMETER";

    public static final String TYPE_MISMATCH =
            "TYPE_MISMATCH";

    public static final String RESOURCE_NOT_FOUND =
            "RESOURCE_NOT_FOUND";

    public static final String METHOD_NOT_ALLOWED =
            "METHOD_NOT_ALLOWED";

    public static final String UNSUPPORTED_MEDIA_TYPE =
            "UNSUPPORTED_MEDIA_TYPE";

    public static final String CONFLICT =
            "CONFLICT";

    public static final String INTERNAL_SERVER_ERROR =
            "INTERNAL_SERVER_ERROR";

    public static final String SERVICE_UNAVAILABLE =
            "SERVICE_UNAVAILABLE_ERROR";

    private WebErrorCodes() {
        throw new IllegalStateException(
                "Constants class must not be instantiated"
        );
    }
}