package com.gl.usersservice.app.exception;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor {

    @Order(value = 1)
    @ExceptionHandler(CustomException.class)
    private ResponseEntity<ErrorMessageDto> handleCustomException(CustomException ex) {
        ErrorMessageDto errorMessageDto = ex.buildExceptionMessageBody();
        return new ResponseEntity<>(errorMessageDto, HttpStatus.valueOf(errorMessageDto.getCode()));
    }

    @Order(value = 99)
    @ExceptionHandler({InternalException.class})
    private ResponseEntity<ErrorMessageDto> handleException(Exception ex) {
        return new ResponseEntity<>(CustomException.buildDefaultExceptionMessageBody(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
