package com.educore.common.dto;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    19/07/2026 at 19:26
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record ApiError(
        String code,
        List<FieldErrorResponse> details
) {

    public static ApiError of(String code) {
        return new ApiError(code, null);
    }

    public static ApiError of(String code, List<FieldErrorResponse> details) {
        return new ApiError(code, details);
    }
}