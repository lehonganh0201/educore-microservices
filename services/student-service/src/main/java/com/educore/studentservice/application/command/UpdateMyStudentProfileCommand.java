package com.educore.studentservice.application.command;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 17:40
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record UpdateMyStudentProfileCommand(
        String phone,
        String address
) {
}
