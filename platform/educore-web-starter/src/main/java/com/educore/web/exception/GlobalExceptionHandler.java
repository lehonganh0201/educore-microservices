package com.educore.web.exception;

import com.educore.common.constant.WebErrorCodes;
import com.educore.common.dto.ApiResponse;
import com.educore.common.dto.FieldErrorResponse;
import com.educore.web.properties.EducoreWebProperties;
import com.educore.web.response.ApiResponseFactory;
import com.educore.web.validation.ValidationErrorMapper;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 11:10
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(
                    GlobalExceptionHandler.class
            );

    private final ApiResponseFactory responseFactory;

    private final ValidationErrorMapper validationErrorMapper;

    private final EducoreWebProperties properties;

    public GlobalExceptionHandler(
            ApiResponseFactory responseFactory,
            ValidationErrorMapper validationErrorMapper,
            EducoreWebProperties properties
    ) {
        this.responseFactory = responseFactory;
        this.validationErrorMapper = validationErrorMapper;
        this.properties = properties;
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(
            ApiException exception
    ) {
        log.warn(
                "API exception code={} status={} message={}",
                exception.getErrorCode(),
                exception.getStatus().value(),
                exception.getMessage()
        );

        ApiResponse<Void> response =
                responseFactory.error(
                        exception.getMessage(),
                        exception.getErrorCode(),
                        emptyToNull(exception.getDetails())
                );

        return ResponseEntity
                .status(exception.getStatus())
                .body(response);
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception
    ) {
        List<FieldErrorResponse> details =
                validationErrorMapper.fromBindingResult(
                        exception.getBindingResult()
                );

        return badRequest(
                "Request validation failed",
                WebErrorCodes.VALIDATION_FAILED,
                details
        );
    }

    @ExceptionHandler(
            IllegalArgumentException.class
    )
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(
            IllegalArgumentException exception
    ) {
        return badRequest(
                exception.getMessage(),
                WebErrorCodes.VALIDATION_FAILED,
                null
        );
    }

    @ExceptionHandler(
            HandlerMethodValidationException.class
    )
    public ResponseEntity<ApiResponse<Void>> handleHandlerMethodValidation(
            HandlerMethodValidationException exception
    ) {
        List<FieldErrorResponse> details =
                validationErrorMapper
                        .fromMethodValidation(exception);

        return badRequest(
                "Request validation failed",
                WebErrorCodes.VALIDATION_FAILED,
                details
        );
    }

    @ExceptionHandler(
            ConstraintViolationException.class
    )
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(
            ConstraintViolationException exception
    ) {
        List<FieldErrorResponse> details =
                validationErrorMapper
                        .fromConstraintViolation(exception);

        return badRequest(
                "Request validation failed",
                WebErrorCodes.VALIDATION_FAILED,
                details
        );
    }

    @ExceptionHandler(
            HttpMessageNotReadableException.class
    )
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception
    ) {
        log.warn(
                "Malformed request body exception={}",
                exception.getClass().getSimpleName()
        );

        return badRequest(
                "Request body is invalid or malformed",
                WebErrorCodes.MALFORMED_REQUEST,
                null
        );
    }

    @ExceptionHandler(
            MissingServletRequestParameterException.class
    )
    public ResponseEntity<ApiResponse<Void>> handleMissingRequestParameter(
            MissingServletRequestParameterException exception
    ) {
        FieldErrorResponse detail =
                new FieldErrorResponse(
                        exception.getParameterName(),
                        "Required parameter is missing"
                );

        return badRequest(
                "Required request parameter is missing",
                WebErrorCodes.MISSING_PARAMETER,
                List.of(detail)
        );
    }

    @ExceptionHandler(
            MethodArgumentTypeMismatchException.class
    )
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception
    ) {
        FieldErrorResponse detail =
                new FieldErrorResponse(
                        exception.getName(),
                        "Value has an invalid type"
                );

        return badRequest(
                "Request parameter has an invalid type",
                WebErrorCodes.TYPE_MISMATCH,
                List.of(detail)
        );
    }

    @ExceptionHandler(
            NoResourceFoundException.class
    )
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFound(
            NoResourceFoundException exception
    ) {
        ApiResponse<Void> response =
                responseFactory.error(
                        "Resource not found",
                        WebErrorCodes.RESOURCE_NOT_FOUND
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(
            HttpRequestMethodNotSupportedException.class
    )
    public ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException exception
    ) {
        ApiResponse<Void> response =
                responseFactory.error(
                        "HTTP method is not supported",
                        WebErrorCodes.METHOD_NOT_ALLOWED
                );

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(response);
    }

    @ExceptionHandler(
            HttpMediaTypeNotSupportedException.class
    )
    public ResponseEntity<ApiResponse<Void>> handleUnsupportedMediaType(
            HttpMediaTypeNotSupportedException exception
    ) {
        ApiResponse<Void> response =
                responseFactory.error(
                        "Media type is not supported",
                        WebErrorCodes.UNSUPPORTED_MEDIA_TYPE
                );

        return ResponseEntity
                .status(
                        HttpStatus.UNSUPPORTED_MEDIA_TYPE
                )
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpectedException(
            Exception exception
    ) {
        log.error(
                "Unhandled application exception",
                exception
        );

        String message =
                resolveInternalErrorMessage(exception);

        ApiResponse<Void> response =
                responseFactory.error(
                        message,
                        WebErrorCodes.INTERNAL_SERVER_ERROR
                );

        return ResponseEntity
                .status(
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
                .body(response);
    }

    private ResponseEntity<ApiResponse<Void>> badRequest(
            String message,
            String errorCode,
            List<FieldErrorResponse> details
    ) {
        ApiResponse<Void> response =
                responseFactory.error(
                        message,
                        errorCode,
                        details
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    private String resolveInternalErrorMessage(
            Exception exception
    ) {
        if (!properties.isIncludeExceptionMessage()) {
            return properties
                    .getInternalServerErrorMessage();
        }

        String exceptionMessage =
                exception.getMessage();

        return exceptionMessage == null
                || exceptionMessage.isBlank()
                ? properties
                .getInternalServerErrorMessage()
                : exceptionMessage;
    }

    private List<FieldErrorResponse> emptyToNull(
            List<FieldErrorResponse> details
    ) {
        return details == null || details.isEmpty()
                ? null
                : details;
    }
}
