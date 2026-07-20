package com.educore.web.validation;

import com.educore.common.dto.FieldErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 11:08
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public final class ValidationErrorMapper {

    private static final String DEFAULT_MESSAGE =
            "Invalid value";

    public List<FieldErrorResponse> fromBindingResult(
            BindingResult bindingResult
    ) {
        List<FieldErrorResponse> fieldErrors =
                bindingResult.getFieldErrors()
                        .stream()
                        .map(this::mapFieldError)
                        .toList();

        List<FieldErrorResponse> globalErrors =
                bindingResult.getGlobalErrors()
                        .stream()
                        .map(this::mapObjectError)
                        .toList();

        return java.util.stream.Stream
                .concat(
                        fieldErrors.stream(),
                        globalErrors.stream()
                )
                .distinct()
                .toList();
    }

    public List<FieldErrorResponse> fromConstraintViolation(
            ConstraintViolationException exception
    ) {
        return exception.getConstraintViolations()
                .stream()
                .map(this::mapConstraintViolation)
                .distinct()
                .toList();
    }

    public List<FieldErrorResponse> fromMethodValidation(
            HandlerMethodValidationException exception
    ) {
        return exception
                .getParameterValidationResults()
                .stream()
                .flatMap(result -> {
                    String parameterName =
                            result.getMethodParameter()
                                    .getParameterName();

                    String field =
                            parameterName == null
                                    ? "parameter"
                                    : parameterName;

                    return result.getResolvableErrors()
                            .stream()
                            .map(error ->
                                    new FieldErrorResponse(
                                            field,
                                            resolveMessage(error)
                                    )
                            );
                })
                .distinct()
                .toList();
    }

    private FieldErrorResponse mapFieldError(
            FieldError error
    ) {
        return new FieldErrorResponse(
                error.getField(),
                resolveMessage(error)
        );
    }

    private FieldErrorResponse mapObjectError(
            ObjectError error
    ) {
        return new FieldErrorResponse(
                error.getObjectName(),
                resolveMessage(error)
        );
    }

    private FieldErrorResponse mapConstraintViolation(
            ConstraintViolation<?> violation
    ) {
        String field = violation.getPropertyPath() == null
                ? "parameter"
                : violation.getPropertyPath().toString();

        String message = violation.getMessage() == null
                || violation.getMessage().isBlank()
                ? DEFAULT_MESSAGE
                : violation.getMessage();

        return new FieldErrorResponse(
                field,
                message
        );
    }

    private String resolveMessage(
            MessageSourceResolvable error
    ) {
        String message = error.getDefaultMessage();

        return message == null || message.isBlank()
                ? DEFAULT_MESSAGE
                : message;
    }
}