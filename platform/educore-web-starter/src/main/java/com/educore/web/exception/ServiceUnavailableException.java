package com.educore.web.exception;

import com.educore.common.constant.WebErrorCodes;
import org.springframework.http.HttpStatus;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 11:27
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public class ServiceUnavailableException extends ApiException{

    public ServiceUnavailableException(String message) {
        this(
                WebErrorCodes.SERVICE_UNAVAILABLE,
                message
        );
    }

    public ServiceUnavailableException(
            String errorCode,
            String message
    ) {
        super(
                HttpStatus.SERVICE_UNAVAILABLE,
                errorCode,
                message
        );
    }
}
