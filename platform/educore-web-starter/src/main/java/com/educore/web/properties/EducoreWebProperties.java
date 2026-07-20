package com.educore.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 11:03
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@ConfigurationProperties(prefix = "edu.web")
public class EducoreWebProperties {

    /**
     * Có trả message thật của exception 500 cho client hay không.
     *
     * Production nên để false để tránh lộ thông tin nội bộ.
     */
    private boolean includeExceptionMessage = false;

    /**
     * Message mặc định cho lỗi không được xử lý.
     */
    private String internalServerErrorMessage =
            "An unexpected error occurred";

    public boolean isIncludeExceptionMessage() {
        return includeExceptionMessage;
    }

    public void setIncludeExceptionMessage(
            boolean includeExceptionMessage
    ) {
        this.includeExceptionMessage =
                includeExceptionMessage;
    }

    public String getInternalServerErrorMessage() {
        return internalServerErrorMessage;
    }

    public void setInternalServerErrorMessage(
            String internalServerErrorMessage
    ) {
        this.internalServerErrorMessage =
                internalServerErrorMessage;
    }
}