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

public class NotFoundException extends ApiException {

    public NotFoundException(String message) {
        this(
                WebErrorCodes.RESOURCE_NOT_FOUND,
                message
        );
    }

    public NotFoundException(
            String errorCode,
            String message
    ) {
        super(
                HttpStatus.NOT_FOUND,
                errorCode,
                message
        );
    }
}