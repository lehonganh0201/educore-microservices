package com.educore.web.response;

import com.educore.common.dto.ApiError;
import com.educore.common.dto.ApiResponse;
import com.educore.common.dto.FieldErrorResponse;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 11:04
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public final class ApiResponseFactory {

    public <T> ApiResponse<T> success(
            String message,
            T data
    ) {
        return ApiResponse.success(
                message,
                data
        );
    }

    public ApiResponse<Void> success(
            String message
    ) {
        return ApiResponse.success(message);
    }

    public ApiResponse<Void> error(
            String message,
            String errorCode
    ) {
        return ApiResponse.error(
                message,
                ApiError.of(errorCode)
        );
    }

    public ApiResponse<Void> error(
            String message,
            String errorCode,
            List<FieldErrorResponse> details
    ) {
        return ApiResponse.error(
                message,
                ApiError.of(
                        errorCode,
                        details
                )
        );
    }
}