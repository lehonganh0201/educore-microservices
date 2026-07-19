package com.educore.commoncore.dto;

import java.time.Instant;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    19/07/2026 at 19:27
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        ApiError error,
        Instant timestamp
) {

    public static <T> ApiResponse<T> success(
            String message,
            T data,
            String path,
            String requestId
    ) {
        return new ApiResponse<>(
                true,
                message,
                data,
                null,
                Instant.now()
        );
    }

    public static ApiResponse<Void> success(
            String message,
            String path,
            String requestId
    ) {
        return new ApiResponse<>(
                true,
                message,
                null,
                null,
                Instant.now()
        );
    }

    public static ApiResponse<Void> error(
            String message,
            ApiError error,
            String path,
            String requestId
    ) {
        return new ApiResponse<>(
                false,
                message,
                null,
                error,
                Instant.now()
        );
    }
}

