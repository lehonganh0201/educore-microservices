package com.educore.web.autoconfigure;

import com.educore.common.dto.ApiResponse;
import com.educore.web.exception.GlobalExceptionHandler;
import com.educore.web.properties.EducoreWebProperties;
import com.educore.web.response.ApiResponseFactory;
import com.educore.web.validation.ValidationErrorMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 11:14
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@AutoConfiguration
@ConditionalOnWebApplication(
        type = ConditionalOnWebApplication.Type.SERVLET
)
@ConditionalOnClass({
        ApiResponse.class,
        RestControllerAdvice.class
})
@ConditionalOnProperty(
        prefix = "edu.web",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@EnableConfigurationProperties(
        EducoreWebProperties.class
)
public class WebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ApiResponseFactory apiResponseFactory() {
        return new ApiResponseFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidationErrorMapper validationErrorMapper() {
        return new ValidationErrorMapper();
    }

    @Bean
    @ConditionalOnMissingBean(
            GlobalExceptionHandler.class
    )
    public GlobalExceptionHandler globalExceptionHandler(
            ApiResponseFactory responseFactory,
            ValidationErrorMapper validationErrorMapper,
            EducoreWebProperties properties
    ) {
        return new GlobalExceptionHandler(
                responseFactory,
                validationErrorMapper,
                properties
        );
    }
}