/*
 * @Author : Thant Htoo Aung
 * @Date : 2/10/2025
 */
package org.oneProjectOneMonth.lms.config.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.oneProjectOneMonth.lms.config.response.dto.PaginatedResponse;

import java.util.Map;

/**
 * Utility class for building pagination metadata.
 */
public class PaginationMetaUtil {

    /**
     * Constructs the pagination metadata if the data is an instance of PaginatedResponse.
     *
     * @param request       the HTTP servlet request.
     * @param page          the current page.
     * @param limit         the limit per page.
     * @param paginatedData the paginated data object.
     * @return a Map containing the pagination metadata or an empty map if data is not paginated.
     */
    public static Map<String, Object> buildPaginationMeta(
            HttpServletRequest request,
            int page,
            int limit,
            Object paginatedData
    ) {
        if (paginatedData instanceof PaginatedResponse<?> paginatedResponse) {
            return Map.of(
                    "method", request.getMethod(),
                    "endpoint", request.getRequestURI(),
                    "current_page", page,
                    "last_page", paginatedResponse.getLastPage(),
                    "limit", limit,
                    "total", paginatedResponse.getTotalItems()
            );
        }
        return Map.of(
                "method", request.getMethod(),
                "endpoint", request.getRequestURI(),
                "current_page", page,
                "last_page", 0,
                "limit", limit,
                "total", 0
        );
    }
}