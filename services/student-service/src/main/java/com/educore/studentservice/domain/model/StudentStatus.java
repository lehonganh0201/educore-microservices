package com.educore.studentservice.domain.model;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:10
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public enum StudentStatus {

    PENDING,
    ACTIVE,
    SUSPENDED,
    GRADUATED,
    WITHDRAWN;

    public boolean canTransitionTo(StudentStatus target) {
        if (target == null) {
            return false;
        }

        if (this == target) {
            return true;
        }

        return switch (this) {
            case PENDING ->
                    target == ACTIVE
                            || target == WITHDRAWN;

            case ACTIVE ->
                    target == SUSPENDED
                            || target == GRADUATED
                            || target == WITHDRAWN;

            case SUSPENDED ->
                    target == ACTIVE
                            || target == WITHDRAWN;

            case GRADUATED, WITHDRAWN -> false;
        };
    }
}
