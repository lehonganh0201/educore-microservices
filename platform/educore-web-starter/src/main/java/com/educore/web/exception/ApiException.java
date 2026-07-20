package com.educore.web.exception;

import com.educore.common.dto.FieldErrorResponse;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 11:06
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public class ApiException extends RuntimeException {

    private final HttpStatus status;

    private final String errorCode;

    private final List<FieldErrorResponse> details;

    public ApiException(
            HttpStatus status,
            String errorCode,
            String message
    ) {
        this(
                status,
                errorCode,
                message,
                List.of()
        );
    }

    public ApiException(
            HttpStatus status,
            String errorCode,
            String message,
            List<FieldErrorResponse> details
    ) {
        super(message);

        this.status = Objects.requireNonNull(
                status,
                "Status must not be null"
        );

        this.errorCode = Objects.requireNonNull(
                errorCode,
                "Error code must not be null"
        );

        this.details = details == null
                ? List.of()
                : List.copyOf(details);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public List<FieldErrorResponse> getDetails() {
        return details;
    }
}
