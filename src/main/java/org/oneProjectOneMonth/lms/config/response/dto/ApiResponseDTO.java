package org.oneProjectOneMonth.lms.config.response.dto;

import org.oneProjectOneMonth.lms.config.response.enums.ResponseStatus;

public record ApiResponseDTO<T>(
        ResponseStatus status,
        String error,
        String message,
        T data
) {
      //success with only data
    public ApiResponseDTO(T data) {
        this(ResponseStatus.SUCCESS, null, null, data);
    }
    public ApiResponseDTO(T data, String message) {
        this(ResponseStatus.SUCCESS, null, message, data);
    }
    public ApiResponseDTO(String message) {
        this(ResponseStatus.SUCCESS, null, message, null);
    }

    //error with data
    public ApiResponseDTO(String error, String message) {
        this(ResponseStatus.FAILED, error, message, null);
    }
    public ApiResponseDTO(String error, String message, T data) {
        this(ResponseStatus.FAILED, error, message, data);
    }

}
