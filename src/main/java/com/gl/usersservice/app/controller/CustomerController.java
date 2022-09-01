package com.gl.usersservice.app.controller;

import com.gl.usersservice.app.dto.LoginRequestDto;
import com.gl.usersservice.app.dto.SignUpRequestDto;
import com.gl.usersservice.app.dto.SignUpResponseDto;
import com.gl.usersservice.app.exception.CustomException;
import com.gl.usersservice.app.facade.CustomerFacade;
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

    private final CustomerFacade customerFacade;

    @PostMapping(value = "/sign-up", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SignUpResponseDto signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) throws CustomException {
        return customerFacade.signUp(signUpRequestDto);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public SignUpResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) throws CustomException {
        return customerFacade.login(loginRequestDto);
    }
}
