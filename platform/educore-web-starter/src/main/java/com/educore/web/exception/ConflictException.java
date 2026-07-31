package com.educore.web.exception;

import com.educore.common.constant.WebErrorCodes;
import org.springframework.http.HttpStatus;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 11:08
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public class ConflictException extends ApiException {

    public ConflictException(String message) {
        this(
                WebErrorCodes.CONFLICT,
                message
        );
    }

    public ConflictException(
            String errorCode,
            String message
    ) {
        super(
                HttpStatus.CONFLICT,
                errorCode,
                message
        );
    }
}
