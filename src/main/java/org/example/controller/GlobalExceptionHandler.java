package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Exception.InvalidDataException;
import org.example.Exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    static class Error {
        private final String reason;
        private final String message;

        public Error(String reason, String message) {
            this.reason = reason;
            this.message = message;
        }

        public String getReason() {
            return reason;
        }

        public String getMessage() {
            return message;
        }
    }

    // 400 for bad request (InvalidDataException or invalid/missing body)
    @ExceptionHandler({InvalidDataException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleInvalidDataException(InvalidDataException ex) {
        log.warn(ex.getMessage());
        return new Error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Request body is missing");
        // Return clean, friendly message
        return new Error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
    }

    // 404 for not found
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleNotFoundException(NotFoundException ex) {
        log.warn(ex.getMessage());
        return new Error(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
    }

    // 500 for unknown exceptions
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleGenericException(Exception ex) {
        log.error("Unexpected error", ex);
        return new Error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
    }
}
