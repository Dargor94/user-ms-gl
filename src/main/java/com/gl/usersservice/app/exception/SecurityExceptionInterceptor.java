package com.gl.usersservice.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SecurityExceptionInterceptor extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ErrorMessageDto> handleJWTVerificationException(CustomException ex) {
        return new ResponseEntity<>(ex.buildExceptionMessageBody(), HttpStatus.UNAUTHORIZED);
    }

}
