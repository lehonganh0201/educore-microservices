package com.educore.identifyservice.domain.exception;

import com.educore.web.exception.NotFoundException;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 11:24
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class AccountNotFoundException extends NotFoundException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
