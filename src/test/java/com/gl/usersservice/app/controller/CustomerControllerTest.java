package com.gl.usersservice.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.usersservice.app.config.JWTFilter;
import com.gl.usersservice.app.dto.LoginRequestDto;
import com.gl.usersservice.app.dto.PhoneDto;
import com.gl.usersservice.app.dto.SignUpRequestDto;
import com.gl.usersservice.app.exception.DtoValidationInterceptor;
import com.gl.usersservice.app.exception.ExceptionInterceptor;
import com.gl.usersservice.app.exception.SecurityExceptionInterceptor;
import com.gl.usersservice.app.service.CustomerServiceImpl;
import com.gl.usersservice.app.service.TokenServiceImpl;
import com.gl.usersservice.app.util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CustomerControllerTest {

    static final String BASE_PATH = "/v1/customer";
    static final String TEST_STRING = "TEST";
    static final String TEST_PASSWORD = "a2asfGfdfdf4";
    static final String TEST_EMAIL = "aaaaaaa@undominio.algo";
    static final int TEST_INT = 1;
    @MockBean
    CustomerServiceImpl customerService;
    @MockBean
    TokenServiceImpl tokenService;
    CustomerController customerController;
    @Autowired
    ExceptionHandlerExceptionResolver resolver;
    @Autowired
    SecurityUtil securityUtil;

    MockMvc mvc;
    JacksonTester<SignUpRequestDto> signUpRequestDtoJacksonTester;
    JacksonTester<LoginRequestDto> loginRequestDtoJacksonTester;
    SignUpRequestDto signUpRequestDto;
    LoginRequestDto loginRequestDto;
    PhoneDto phoneDto;

    @BeforeEach
    public void setup() {
        customerController = new CustomerController(customerService);
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(ExceptionInterceptor.class,
                        DtoValidationInterceptor.class,
                        SecurityExceptionInterceptor.class)
                .addFilters(new JWTFilter(customerService, tokenService, securityUtil))
                .build();
    }


    @Test
    @DisplayName("Successful Sign Up")
    void signUp_Then_Success() throws Exception {

        setPhoneDto(TEST_INT, TEST_INT, TEST_STRING);
        setSignUpRequestDto(TEST_EMAIL, TEST_STRING, TEST_PASSWORD, phoneDto);

        MockHttpServletResponse response = mvc.perform(post(BASE_PATH + "/sign-up")
                        .content(signUpRequestDtoJacksonTester.write(signUpRequestDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Sign Up - Throws Exception - Invalid Dto")
    void signUp_Then_Dto_Validation_Exception() throws Exception {

        setPhoneDto(TEST_INT, TEST_INT, TEST_STRING);
        setSignUpRequestDto(TEST_STRING, TEST_STRING, TEST_STRING, phoneDto);

        MockHttpServletResponse response = mvc.perform(post(BASE_PATH + "/sign-up")
                        .content(signUpRequestDtoJacksonTester.write(signUpRequestDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("Successful Login")
    void login_Then_Success() throws Exception {

        setLoginRequestDto(TEST_EMAIL, TEST_PASSWORD);

        MockHttpServletResponse response = mvc.perform(post(BASE_PATH + "/login")
                        .content(loginRequestDtoJacksonTester.write(loginRequestDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Login - Throws Exception - Invalid Dto")
    void login_Then_Dto_Validation_Exception() throws Exception {

        setLoginRequestDto("", "");

        MockHttpServletResponse response = mvc.perform(post(BASE_PATH + "/login")
                        .content(loginRequestDtoJacksonTester.write(loginRequestDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    void setSignUpRequestDto(String email, String name, String password, PhoneDto phoneDto) {
        signUpRequestDto = new SignUpRequestDto();

        signUpRequestDto.setEmail(email);
        signUpRequestDto.setName(name);
        signUpRequestDto.setPassword(password);
        signUpRequestDto.setPhones(Collections.singletonList(phoneDto));
    }

    void setPhoneDto(long number, int cityCode, String countryCode) {
        phoneDto = new PhoneDto();

        phoneDto.setNumber(number);
        phoneDto.setCityCode(cityCode);
        phoneDto.setCountryCode(countryCode);
    }

    void setLoginRequestDto(String email, String password) {
        loginRequestDto = new LoginRequestDto();

        loginRequestDto.setEmail(email);
        loginRequestDto.setPassword(password);

    }
}