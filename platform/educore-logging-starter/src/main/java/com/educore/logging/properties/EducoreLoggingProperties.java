package com.educore.logging.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 10:13
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@ConfigurationProperties(prefix = "edu.logging.http")
public class EducoreLoggingProperties {

    /**
     * Bật hoặc tắt HTTP logging filter.
     */
    private boolean enabled = true;

    /**
     * Ghi access log sau khi request hoàn thành.
     */
    private boolean accessLogEnabled = true;

    /**
     * Tên header chứa request ID.
     */
    private String requestIdHeader = "X-Request-Id";

    /**
     * Độ dài tối đa của request ID nhận từ client.
     */
    private int maxRequestIdLength = 128;

    /**
     * Có đưa query string vào log hay không.
     * Production nên để false.
     */
    private boolean includeQueryString = false;

    /**
     * Request vượt quá thời gian này được ghi ở mức WARN.
     */
    private Duration slowRequestThreshold =
            Duration.ofSeconds(1);

    /**
     * Những endpoint không cần ghi access log.
     */
    private List<String> excludedPaths =
            new ArrayList<>(
                    List.of(
                            "/actuator/health",
                            "/actuator/health/**",
                            "/actuator/prometheus",
                            "/favicon.ico"
                    )
            );

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccessLogEnabled() {
        return accessLogEnabled;
    }

    public void setAccessLogEnabled(
            boolean accessLogEnabled
    ) {
        this.accessLogEnabled = accessLogEnabled;
    }

    public String getRequestIdHeader() {
        return requestIdHeader;
    }

    public void setRequestIdHeader(
            String requestIdHeader
    ) {
        this.requestIdHeader = requestIdHeader;
    }

    public int getMaxRequestIdLength() {
        return maxRequestIdLength;
    }

    public void setMaxRequestIdLength(
            int maxRequestIdLength
    ) {
        this.maxRequestIdLength = maxRequestIdLength;
    }

    public boolean isIncludeQueryString() {
        return includeQueryString;
    }

    public void setIncludeQueryString(
            boolean includeQueryString
    ) {
        this.includeQueryString = includeQueryString;
    }

    public Duration getSlowRequestThreshold() {
        return slowRequestThreshold;
    }

    public void setSlowRequestThreshold(
            Duration slowRequestThreshold
    ) {
        this.slowRequestThreshold =
                slowRequestThreshold;
    }

    public List<String> getExcludedPaths() {
        return excludedPaths;
    }

    public void setExcludedPaths(
            List<String> excludedPaths
    ) {
        this.excludedPaths =
                excludedPaths == null
                        ? new ArrayList<>()
                        : new ArrayList<>(excludedPaths);
    }
}
