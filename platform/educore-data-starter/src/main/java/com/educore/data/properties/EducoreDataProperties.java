package com.educore.data.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 11:26
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@ConfigurationProperties(prefix = "edu.data.pagination")
public class EducoreDataProperties {

    private int defaultPage = 0;

    private int defaultPageSize = 20;

    private int maxPageSize = 100;

    private String defaultSortDirection = "DESC";

    public int getDefaultPage() {
        return defaultPage;
    }

    public void setDefaultPage(int defaultPage) {
        this.defaultPage = defaultPage;
    }

    public int getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public int getMaxPageSize() {
        return maxPageSize;
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public String getDefaultSortDirection() {
        return defaultSortDirection;
    }

    public void setDefaultSortDirection(
            String defaultSortDirection
    ) {
        this.defaultSortDirection =
                defaultSortDirection;
    }
}