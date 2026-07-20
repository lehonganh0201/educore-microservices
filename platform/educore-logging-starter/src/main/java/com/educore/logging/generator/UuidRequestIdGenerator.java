package com.educore.logging.generator;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 10:14
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public final class UuidRequestIdGenerator implements RequestIdGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}