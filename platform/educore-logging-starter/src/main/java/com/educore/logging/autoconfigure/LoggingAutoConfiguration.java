package com.educore.logging.autoconfigure;

import com.educore.logging.filter.HttpRequestLoggingFilter;
import com.educore.logging.generator.RequestIdGenerator;
import com.educore.logging.generator.UuidRequestIdGenerator;
import com.educore.logging.properties.EducoreLoggingProperties;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.EnumSet;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 10:19
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@AutoConfiguration
@ConditionalOnWebApplication(
        type = ConditionalOnWebApplication.Type.SERVLET
)
@ConditionalOnClass({
        Filter.class,
        OncePerRequestFilter.class,
        MDC.class
})
@ConditionalOnProperty(
        prefix = "edu.logging.http",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@EnableConfigurationProperties(
        EducoreLoggingProperties.class
)
public class LoggingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RequestIdGenerator requestIdGenerator() {
        return new UuidRequestIdGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpRequestLoggingFilter httpRequestLoggingFilter(
            EducoreLoggingProperties properties,
            RequestIdGenerator requestIdGenerator
    ) {
        return new HttpRequestLoggingFilter(
                properties,
                requestIdGenerator
        );
    }

    @Bean
    public FilterRegistrationBean<HttpRequestLoggingFilter> httpRequestLoggingFilterRegistration(
            HttpRequestLoggingFilter filter
    ) {
        FilterRegistrationBean<HttpRequestLoggingFilter>
                registration =
                new FilterRegistrationBean<>();

        registration.setFilter(filter);

        registration.setName(
                "educoreHttpRequestLoggingFilter"
        );

        registration.setOrder(
                Ordered.HIGHEST_PRECEDENCE + 20
        );

        registration.setDispatcherTypes(
                EnumSet.of(DispatcherType.REQUEST)
        );

        registration.addUrlPatterns("/*");

        return registration;
    }
}