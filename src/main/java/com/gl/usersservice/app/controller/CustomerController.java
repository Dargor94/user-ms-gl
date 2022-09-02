package com.gl.usersservice.app.controller;

import com.gl.usersservice.app.dto.CustomerResponseDto;
import com.gl.usersservice.app.dto.LoginRequestDto;
import com.gl.usersservice.app.dto.SignUpRequestDto;
import com.gl.usersservice.app.exception.CustomException;
import com.gl.usersservice.app.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@ResponseStatus
@AllArgsConstructor
@RequestMapping("/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(value = "/sign-up", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CustomerResponseDto signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) throws CustomException {
        return customerService.signUp(signUpRequestDto);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) throws CustomException {
        return customerService.login(loginRequestDto);
    }
}
