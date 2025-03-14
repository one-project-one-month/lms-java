package org.oneProjectOneMonth.lms.config.exception.handler;


import org.oneProjectOneMonth.lms.config.exception.CoreApiException;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CoreApiExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CoreApiException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleCoreApiException(CoreApiException ex) {
        var errorResponse = new ApiResponseDTO<>(
                "Core Api Exception", ex.getMessage(), ex.getDetails()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
