package com.educore.studentservice.domain.exception;

import com.educore.web.exception.ConflictException;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:12
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class StudentAlreadyExistsException extends ConflictException {
    public StudentAlreadyExistsException(String message) {
        super(message);
    }
}
