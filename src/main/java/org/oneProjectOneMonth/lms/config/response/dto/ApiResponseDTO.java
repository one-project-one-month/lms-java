package org.oneProjectOneMonth.lms.config.response.dto;

import org.oneProjectOneMonth.lms.config.response.enums.ResponseStatus;
import java.util.Map;

public record ApiResponseDTO<T>(
        ResponseStatus status,
        T data,
        String error,
        String message,
        Map<String, String> details
) {

    // success with only data
    public ApiResponseDTO(T data) {
        this(ResponseStatus.SUCCESS, data, null, null, null);
    }

    public ApiResponseDTO(T data, String message) {
        this(ResponseStatus.SUCCESS, data, null, message, null);
    }

    public ApiResponseDTO(String message) {
        this(ResponseStatus.SUCCESS, null, null, message, null);
    }

    public ApiResponseDTO(String error, String message) {
        this(ResponseStatus.FAILED, null, error, message, null);
    }

    //error with details
    public ApiResponseDTO(String error, String message, Map<String, String> details) {
        this(ResponseStatus.FAILED, null, error, message, details);
    }
}
