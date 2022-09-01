package com.gl.usersservice.app.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class LoginRequestDto {

    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

}
