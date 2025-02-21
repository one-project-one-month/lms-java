package org.oneProjectOneMonth.lms.config.response.utils;

import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponseDTO<T>> buildResponse(
            ApiResponseDTO<T> response,
            HttpStatus status) {

        return new ResponseEntity<>(response, status);
    }
}
