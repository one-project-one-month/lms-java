//package org.oneProjectOneMonth.lms.config.exception;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.ConstraintViolationException;
//import org.apache.coyote.BadRequestException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
//import org.oneProjectOneMonth.lms.config.response.enums.ResponseStatus;
//
///**
// * Global exception handler for centralized exception management across the application.
// * Provides tailored responses for various exception types to ensure consistent and user-friendly error messages.
// */
//@ControllerAdvice
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//
//    /**
//     * Handles IllegalArgumentExceptions, typically thrown when method arguments are invalid or inappropriate.
//     *
//     * @param ex      the IllegalArgumentException encountered.
//     * @param request the current HTTP request.
//     * @return a ResponseEntity containing the standardized ApiResponseDTO.
//     */
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ApiResponseDTO<String>> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid argument provided.", ex.getMessage(), request);
//    }
//
//    /**
//     * Handles ConstraintViolationException, typically occurring during input validation.
//     *
//     * @param ex      the ConstraintViolationException encountered.
//     * @param request the current HTTP request.
//     * @return a ResponseEntity containing the standardized ApiResponseDTO.
//     */
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ApiResponseDTO<String>> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
//        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed.", ex.getMessage(), request);
//    }
//
//    /**
//     * Handles EntityNotFoundException, thrown when an entity cannot be located in the database.
//     *
//     * @param ex      the EntityNotFoundException encountered.
//     * @param request the current HTTP request.
//     * @return a ResponseEntity containing the standardized ApiResponseDTO.
//     */
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<ApiResponseDTO<String>> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
//        return buildErrorResponse(HttpStatus.NOT_FOUND, "Entity not found.", ex.getMessage(), request);
//    }
//
//    /**
//     * Handles DuplicateEntityException, thrown when attempting to create an entity that already exists.
//     *
//     * @param ex      the DuplicateEntityException encountered.
//     * @param request the current HTTP request.
//     * @return a ResponseEntity containing the standardized ApiResponseDTO.
//     */
//    @ExceptionHandler(DuplicateEntityException.class)
//    public ResponseEntity<ApiResponseDTO<String>> handleDuplicateEntityException(DuplicateEntityException ex, HttpServletRequest request) {
//        return buildErrorResponse(HttpStatus.CONFLICT, "Duplicate entity detected.", ex.getMessage(), request);
//    }
//
//    /**
//     * Handles BadRequestException, thrown when the client request contains invalid data.
//     *
//     * @param ex      the BadRequestException encountered.
//     * @param request the current HTTP request.
//     * @return a ResponseEntity containing the standardized ApiResponseDTO.
//     */
//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<ApiResponseDTO<String>> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad request.", ex.getMessage(), request);
//    }
//
//    /**
//     * Handles SecurityException, typically thrown when authentication or authorization fails.
//     *
//     * @param ex      the SecurityException encountered.
//     * @param request the current HTTP request.
//     * @return a ResponseEntity containing the standardized ApiResponseDTO.
//     */
//    @ExceptionHandler(SecurityException.class)
//    public ResponseEntity<ApiResponseDTO<String>> handleSecurityException(SecurityException ex, HttpServletRequest request) {
//        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), "Security violation.", request);
//    }
//
//    /**
//     * Handles all uncaught exceptions, providing a generic error response.
//     *
//     * @param ex      the Exception encountered.
//     * @param request the current HTTP request.
//     * @return a ResponseEntity containing the standardized ApiResponseDTO.
//     */
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponseDTO<String>> handleGlobalException(Exception ex, HttpServletRequest request) {
//        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.", ex.getMessage(), request);
//    }
//
//    /**
//     * Utility method to construct a standardized error response.
//     *
//     * @param status  the HTTP status.
//     * @param message a brief error description.
//     * @param details additional details about the error.
//     * @param request the HTTP request causing the error.
//     * @return a ResponseEntity containing the ApiResponseDTO.
//     */
//    private ResponseEntity<ApiResponseDTO<String>> buildErrorResponse(HttpStatus status, String message, String details, HttpServletRequest request) {
//        ApiResponseDTO errorResponse = new ApiResponseDTO<>(
//                ResponseStatus.FAILED,
//                details,
//                message,
//                null
//        );
//
//        return new ResponseEntity<>(errorResponse, status);
//    }
//}
