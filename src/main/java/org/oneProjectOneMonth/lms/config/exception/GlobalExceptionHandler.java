/*
 * @Author : Thant Htoo Aung
 * @Date : 2/10/2025
 */
package org.oneProjectOneMonth.lms.config.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.BadRequestException;
import org.oneProjectOneMonth.lms.config.response.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Map;

/**
 * Global exception handler for centralized exception management across the application.
 * Provides tailored responses for various exception types to ensure consistent and user-friendly error messages.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles IllegalArgumentExceptions, typically thrown when method arguments are invalid or inappropriate.
     *
     * @param ex      the IllegalArgumentException encountered.
     * @param request the current HTTP request.
     * @return a ResponseEntity containing the standardized ApiResponse.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid argument provided.", ex.getMessage(), request);
    }

    /**
     * Handles ConstraintViolationException, typically occurring during input validation.
     *
     * @param ex      the ConstraintViolationException encountered.
     * @param request the current HTTP request.
     * @return a ResponseEntity containing the standardized ApiResponse.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed.", ex.getMessage(), request);
    }

    /**
     * Handles EntityNotFoundException, thrown when an entity cannot be located in the database.
     *
     * @param ex      the EntityNotFoundException encountered.
     * @param request the current HTTP request.
     * @return a ResponseEntity containing the standardized ApiResponse.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Entity not found.", ex.getMessage(), request);
    }

    /**
     * Handles DuplicateEntityException, thrown when attempting to create an entity that already exists.
     *
     * @param ex      the DuplicateEntityException encountered.
     * @param request the current HTTP request.
     * @return @return a ResponseEntity containing the standardized ApiResponse.
     */
    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ApiResponse> handleDuplicateEntityException(DuplicateEntityException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, "Duplicate entity detected.", ex.getMessage(), request);
    }

    /**
     * Handles BadRequestException, thrown when the client request contains invalid data.
     *
     * @param ex      the BadRequestException encountered.
     * @param request the current HTTP request.
     * @return a ResponseEntity containing the standardized ApiResponse.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad request.", ex.getMessage(), request);
    }

    /**
     * Handles SecurityException, typically thrown when authentication or authorization fails.
     *
     * @param ex      the SecurityException encountered.
     * @param request the current HTTP request.
     * @return a ResponseEntity containing the standardized ApiResponse.
     */
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiResponse> handleSecurityException(SecurityException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), "Security violation.", request);
    }

    /**
     * Handles all uncaught exceptions, providing a generic error response.
     *
     * @param ex      the Exception encountered.
     * @param request the current HTTP request.
     * @return a ResponseEntity containing the standardized ApiResponse.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalException(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.", ex.getMessage(), request);
    }

    /**
     * Utility method to construct a standardized error response.
     *
     * @param status  the HTTP status.
     * @param message a brief error description.
     * @param details additional details about the error.
     * @param request the HTTP request causing the error.
     * @return a ResponseEntity containing the ApiResponse.
     */
    private ResponseEntity<ApiResponse> buildErrorResponse(HttpStatus status, String message, String details, HttpServletRequest request) {
        ApiResponse errorResponse = ApiResponse.builder()
                .success(0)
                .code(status.value())
                .message(message)
                .data(details)
                .meta(Map.of(
                        "method", request.getMethod(),
                        "endpoint", request.getRequestURI()
                ))
                .duration(Instant.now().getEpochSecond())
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }
}