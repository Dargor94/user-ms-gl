package com.gl.usersservice.app.exception;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ResponseStatus
@ControllerAdvice
public class DtoValidationInterceptor extends ResponseEntityExceptionHandler {

    @Override
    @ResponseBody
    @ExceptionHandler(InternalException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus httpStatus, WebRequest request) {
        return ResponseEntity.of(getErrorBody(ex.getBindingResult().getFieldErrors(), httpStatus));
    }

    private Optional<Object> getErrorBody(List<FieldError> errors, HttpStatus httpStatus) {
        return Optional.of(new ValidationErrorDto(errors, httpStatus));
    }

    @Setter
    @Getter
    public static class ValidationErrorDto implements Serializable {

        private static final long serialVersionUID = -1583808350123366917L;

        private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        private int code;
        private List<String> detail;

        public ValidationErrorDto(List<FieldError> errors, HttpStatus httpStatus) {
            this.code = httpStatus.value();
            this.detail = getErrors(errors);
        }

        private List<String> getErrors(List<FieldError> errors) {
            return errors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage
            ).collect(Collectors.toList());
        }
    }


}
