package com.gl.usersservice.app.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@ResponseStatus
@ControllerAdvice
public class SecurityExceptionInterceptor extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({JWTVerificationException.class, JWTDecodeException.class})
    public ResponseEntity<ErrorMessageDto> handleJWTVerificationException(CustomException ex) {
        return ResponseEntity.of(Optional.of(ex.buildExceptionMessageBody()));
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessageDto> handleAuthenticationException(CustomException ex) {
        return ResponseEntity.of(Optional.of(ex.buildExceptionMessageBody()));
    }

}
