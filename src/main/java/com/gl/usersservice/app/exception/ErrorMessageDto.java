package com.gl.usersservice.app.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
public class ErrorMessageDto implements Serializable {

    private static final long serialVersionUID = 1400958076992673864L;

    private Timestamp timestamp;
    private int code;
    private String detail;

    public ErrorMessageDto(int code, String detail) {
        this.code = code;
        this.detail = detail;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
}
