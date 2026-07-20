package com.educore.data.pagination;

import com.educore.common.dto.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 11:34
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public final class SpringPageResponseMapper {

    private SpringPageResponseMapper() {
        throw new IllegalStateException(
                "Utility class must not be instantiated"
        );
    }

    /**
     * Chuyển trực tiếp Page<T> thành PageResponse<T>.
     */
    public static <T> PageResponse<T> from(
            Page<T> page
    ) {
        Objects.requireNonNull(
                page,
                "Page must not be null"
        );

        return build(
                page,
                page.getContent()
        );
    }

    /**
     * Chuyển Page<S> thành PageResponse<T>,
     * đồng thời ánh xạ từng phần tử từ Entity sang Response DTO.
     */
    public static <S, T> PageResponse<T> from(
            Page<S> page,
            Function<? super S, T> mapper
    ) {
        Objects.requireNonNull(
                page,
                "Page must not be null"
        );

        Objects.requireNonNull(
                mapper,
                "Mapper must not be null"
        );

        List<T> content = page.getContent()
                .stream()
                .map(mapper)
                .toList();

        return build(
                page,
                content
        );
    }

    private static <T> PageResponse<T> build(
            Page<?> page,
            List<T> content
    ) {
        PageResponse.PageMetadata metadata =
                new PageResponse.PageMetadata(
                        page.getNumber(),
                        page.getSize(),
                        page.getTotalElements(),
                        page.getTotalPages(),
                        page.isFirst(),
                        page.isLast()
                );

        List<PageResponse.SortItem> sortItems =
                page.getSort()
                        .stream()
                        .map(order ->
                                new PageResponse.SortItem(
                                        order.getProperty(),
                                        mapDirection(
                                                order.getDirection()
                                                        .name()
                                        )
                                )
                        )
                        .toList();

        return new PageResponse<>(
                content,
                metadata,
                sortItems
        );
    }

    private static PageResponse.SortDirection mapDirection(
            String direction
    ) {
        return PageResponse.SortDirection.valueOf(
                direction
        );
    }
}