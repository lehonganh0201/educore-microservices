package com.educore.common.dto;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    19/07/2026 at 19:28
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record PageResponse<T>(
        List<T> content,
        PageMetadata page,
        List<SortItem> sort
) {

    public PageResponse {
        content = content == null
                ? List.of()
                : List.copyOf(content);

        sort = sort == null
                ? List.of()
                : List.copyOf(sort);
    }

    public record PageMetadata(
            int number,
            int size,
            long totalElements,
            int totalPages,
            boolean first,
            boolean last
    ) {
    }

    public record SortItem(
            String property,
            SortDirection direction
    ) {
    }

    public enum SortDirection {
        ASC,
        DESC
    }
}