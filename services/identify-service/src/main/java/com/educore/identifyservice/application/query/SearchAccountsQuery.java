package com.educore.identifyservice.application.query;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 12:33
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record SearchAccountsQuery(
        String keyword,
        Integer page,
        Integer size
) {

    public SearchAccountsQuery {
        keyword = keyword == null
                ? ""
                : keyword.trim();
    }
}