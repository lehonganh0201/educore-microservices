package com.educore.web.exception;

import com.educore.common.constant.WebErrorCodes;
import org.springframework.http.HttpStatus;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 11:07
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        this(
                WebErrorCodes.BAD_REQUEST,
                message
        );
    }

    public BadRequestException(
            String errorCode,
            String message
    ) {
        super(
                HttpStatus.BAD_REQUEST,
                errorCode,
                message
        );
    }
}
