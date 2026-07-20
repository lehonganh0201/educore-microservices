package com.educore.data.pagination;

import com.educore.data.properties.EducoreDataProperties;
import com.educore.web.exception.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Locale;
import java.util.Set;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 11:28
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public final class PageableFactory {

    private final EducoreDataProperties properties;

    public PageableFactory(
            EducoreDataProperties properties
    ) {
        this.properties = properties;
    }

    public Pageable create(
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection,
            String defaultSortField,
            Set<String> allowedSortFields
    ) {
        int resolvedPage = resolvePage(page);
        int resolvedSize = resolveSize(size);

        String resolvedSortField = resolveSortField(
                sortBy,
                defaultSortField,
                allowedSortFields
        );

        Sort.Direction resolvedDirection =
                resolveDirection(sortDirection);

        return PageRequest.of(
                resolvedPage,
                resolvedSize,
                Sort.by(
                        resolvedDirection,
                        resolvedSortField
                )
        );
    }

    private int resolvePage(Integer page) {
        int resolvedPage = page == null
                ? properties.getDefaultPage()
                : page;

        if (resolvedPage < 0) {
            throw new BadRequestException(
                    "Page index must not be negative"
            );
        }

        return resolvedPage;
    }

    private int resolveSize(Integer size) {
        int resolvedSize = size == null
                ? properties.getDefaultPageSize()
                : size;

        if (resolvedSize <= 0) {
            throw new BadRequestException(
                    "Page size must be greater than 0"
            );
        }

        if (resolvedSize > properties.getMaxPageSize()) {
            throw new BadRequestException(
                    "Page size must not exceed "
                            + properties.getMaxPageSize()
            );
        }

        return resolvedSize;
    }

    private String resolveSortField(
            String sortBy,
            String defaultSortField,
            Set<String> allowedSortFields
    ) {
        if (defaultSortField == null
                || defaultSortField.isBlank()) {
            throw new BadRequestException(
                    "Default sort field must not be blank"
            );
        }

        if (allowedSortFields == null
                || allowedSortFields.isEmpty()) {
            throw new BadRequestException(
                    "Allowed sort fields must not be empty"
            );
        }

        if (!allowedSortFields.contains(defaultSortField)) {
            throw new BadRequestException(
                    "Default sort field must be allowed"
            );
        }

        String resolvedSortField =
                sortBy == null || sortBy.isBlank()
                        ? defaultSortField
                        : sortBy.trim();

        if (!allowedSortFields.contains(resolvedSortField)) {
            throw new BadRequestException(
                    "Invalid sort field: "
                            + resolvedSortField
            );
        }

        return resolvedSortField;
    }

    private Sort.Direction resolveDirection(
            String sortDirection
    ) {
        String resolvedDirection =
                sortDirection == null
                        || sortDirection.isBlank()
                        ? properties
                        .getDefaultSortDirection()
                        : sortDirection;

        try {
            return Sort.Direction.valueOf(
                    resolvedDirection
                            .trim()
                            .toUpperCase(Locale.ROOT)
            );
        } catch (IllegalArgumentException | BadRequestException exception) {
            throw new BadRequestException(
                    "Invalid sort direction: "
                            + sortDirection
            );
        }
    }
}