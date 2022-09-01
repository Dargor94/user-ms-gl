package com.gl.usersservice.app.facade;

import com.gl.usersservice.app.dto.LoginRequestDto;
import com.gl.usersservice.app.dto.SignUpRequestDto;
import com.gl.usersservice.app.dto.SignUpResponseDto;
import com.gl.usersservice.app.exception.CustomException;
import com.gl.usersservice.app.service.AuthenticationService;
import com.gl.usersservice.app.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerFacade {

    private final CustomerService customerService;
    private final AuthenticationService authenticationService;

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) throws CustomException {
        return this.customerService.signUp(signUpRequestDto);
    }

    public SignUpResponseDto login(LoginRequestDto loginRequestDto) throws CustomException {
        authenticationService.authenticate(loginRequestDto);
        return this.customerService.login(loginRequestDto);
    }

}
