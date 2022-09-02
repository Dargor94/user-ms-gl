package com.gl.usersservice.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
public class CustomException extends Throwable {

    private static final long serialVersionUID = 3607364700905310855L;

    private final ExceptionDefinition exceptionDefinition;

    public CustomException(ExceptionDefinition exceptionDefinition) {
        super(exceptionDefinition.detail);
        this.exceptionDefinition = exceptionDefinition;
    }

    public static ErrorMessageDto buildDefaultExceptionMessageBody() {
        return new ErrorMessageDto(ExceptionDefinition.INTERNAL_SERVER_ERROR.code,
                ExceptionDefinition.INTERNAL_SERVER_ERROR.detail);
    }

    public ErrorMessageDto buildExceptionMessageBody() {
        return new ErrorMessageDto(this.exceptionDefinition.code,
                this.exceptionDefinition.detail);
    }

    @Getter
    public enum ExceptionDefinition {
        CUSTOMER_ALREADY_EXISTS_ERROR(HttpStatus.CONFLICT.value(), "Customer already exists."),
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There was an internal error. Try again later"),
        CUSTOMER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND.value(), "Customer was not found"),
        ;
        private final Timestamp timestamp;
        private final int code;
        private final String detail;

        ExceptionDefinition(int code, String detail) {
            this.code = code;
            this.detail = detail;
            this.timestamp = new Timestamp(System.currentTimeMillis());
        }
    }


}
