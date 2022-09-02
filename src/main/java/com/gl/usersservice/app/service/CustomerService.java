package com.gl.usersservice.app.service;

import com.gl.usersservice.app.dto.CustomerResponseDto;
import com.gl.usersservice.app.dto.LoginRequestDto;
import com.gl.usersservice.app.dto.SignUpRequestDto;
import com.gl.usersservice.app.exception.CustomException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerService extends UserDetailsService {

    CustomerResponseDto signUp(SignUpRequestDto signUpRequestDto) throws CustomException;

    CustomerResponseDto login(LoginRequestDto loginRequestDto) throws CustomException;


}
