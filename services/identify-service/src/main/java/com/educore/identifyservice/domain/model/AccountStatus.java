package com.educore.identifyservice.domain.model;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 10:59
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public enum AccountStatus {
    ACTIVE,
    DISABLED;

    public static AccountStatus fromEnabled(boolean enabled) {
        return enabled ? ACTIVE : DISABLED;
    }
}
