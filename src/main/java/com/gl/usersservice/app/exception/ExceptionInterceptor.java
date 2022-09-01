package com.gl.usersservice.app.exception;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@ResponseStatus
@ControllerAdvice
public class ExceptionInterceptor {

    @ResponseBody
    @ExceptionHandler(CustomException.class)
    private ResponseEntity<ErrorMessageDto> handleCustomException(CustomException ex) {
        return ResponseEntity.of(Optional.of(ex.buildExceptionMessageBody()));
    }

    @ResponseBody
    @ExceptionHandler(InternalException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ErrorMessageDto> handleException(Exception ex) {
        return ResponseEntity.of(Optional.of(CustomException.buildDefaultExceptionMessageBody(ex)));
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ErrorMessageDto> handleRuntimeException(Exception ex) {
        return ResponseEntity.of(Optional.of(CustomException.buildDefaultExceptionMessageBody(ex)));
    }

}
