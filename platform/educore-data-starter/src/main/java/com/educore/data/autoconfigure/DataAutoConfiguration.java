package com.educore.data.autoconfigure;

import com.educore.data.pagination.PageableFactory;
import com.educore.data.properties.EducoreDataProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 11:36
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@AutoConfiguration
@ConditionalOnClass(Pageable.class)
@EnableConfigurationProperties(
        EducoreDataProperties.class
)
public class DataAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PageableFactory pageableFactory(
            EducoreDataProperties properties
    ) {
        return new PageableFactory(properties);
    }
}
