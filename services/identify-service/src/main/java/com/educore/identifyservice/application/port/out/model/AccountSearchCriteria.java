package com.educore.identifyservice.application.port.out.model;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 12:34
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record AccountSearchCriteria(
        String keyword,
        int page,
        int size
) {

    public int offset() {
        return page * size;
    }
}