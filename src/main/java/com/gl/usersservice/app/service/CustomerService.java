package com.gl.usersservice.app.service;

import com.gl.usersservice.app.dto.LoginRequestDto;
import com.gl.usersservice.app.dto.SignUpRequestDto;
import com.gl.usersservice.app.dto.SignUpResponseDto;
import com.gl.usersservice.app.exception.CustomException;
import com.gl.usersservice.core.entity.Customer;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface CustomerService extends UserDetailsService {

    SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) throws CustomException;

    SignUpResponseDto login(LoginRequestDto loginRequestDto) throws CustomException;

    Optional<Customer> findByEmail(String email);

}
