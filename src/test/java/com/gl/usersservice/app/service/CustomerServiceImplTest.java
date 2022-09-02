package com.gl.usersservice.app.service;

import com.gl.usersservice.app.dto.CustomerResponseDto;
import com.gl.usersservice.app.dto.LoginRequestDto;
import com.gl.usersservice.app.dto.PhoneDto;
import com.gl.usersservice.app.dto.SignUpRequestDto;
import com.gl.usersservice.app.exception.CustomException;
import com.gl.usersservice.core.entity.Customer;
import com.gl.usersservice.core.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class CustomerServiceImplTest {

    static final String TEST_STRING = "TEST";
    static final int TEST_INT = 1;
    CustomerServiceImpl customerService;
    @MockBean
    CustomerRepository customerRepository;
    SignUpRequestDto signUpRequestDto;
    PhoneDto phoneDto;
    CustomerResponseDto customerResponseDto;
    Customer customer;
    LoginRequestDto loginRequestDto;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository);
    }


    @Test
    @DisplayName("Successful sign up")
    void signUp_Then_Success() throws CustomException {

        setSignUpRequestDto(TEST_STRING, TEST_STRING, TEST_STRING, phoneDto);

        given(customerRepository.findByEmail(TEST_STRING))
                .willReturn(Optional.empty());

        given(customerRepository.save(customer))
                .willAnswer(invocation -> invocation.getArgument(0));

        customerResponseDto = customerService.signUp(signUpRequestDto);

        assertThat(customerResponseDto).isNotNull();
        verify(customerRepository).save(any(Customer.class));

    }

    @Test
    @DisplayName("Sign Up - Throws Exception - Customer exists")
    void signUp_Then_Exception_Customer_Exists() {

        setPhoneDto(TEST_INT, TEST_INT, TEST_STRING);
        setSignUpRequestDto(TEST_STRING, TEST_STRING, TEST_STRING, phoneDto);

        customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());

        given(customerRepository.findByEmail(TEST_STRING))
                .willReturn(Optional.ofNullable(customer));


        assertThrows(CustomException.class, () -> customerService.signUp(signUpRequestDto));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Successful Login")
    void login_Then_Success() throws CustomException {

        setLoginRequestDto(TEST_STRING, TEST_STRING);

        customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());

        given(customerRepository.findByEmail(TEST_STRING))
                .willReturn(Optional.ofNullable(customer));

        customerResponseDto = customerService.login(loginRequestDto);

        assertThat(customerResponseDto).isNotNull();
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Login - Throws Exception - Customer doesn't exists")
    void login_Then_Exception_Customer_Does_Not_Exists() {

        setLoginRequestDto(TEST_STRING, TEST_STRING);

        customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());

        given(customerRepository.findByEmail(TEST_STRING))
                .willReturn(Optional.empty());

        assertThrows(CustomException.class, () -> customerService.login(loginRequestDto));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Load customer by userName (email) is successful")
    void loadCustomerByUsername_Then_Success() {

        customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());
        customer.setPassword(TEST_STRING);
        given(customerRepository.findByEmail(TEST_STRING))
                .willReturn(Optional.ofNullable(customer));

        UserDetails userDetails = customerService.loadUserByUsername(TEST_STRING);

        assertThat(userDetails).isNotNull();

    }

    @Test
    @DisplayName("Load customer by userName (email) - Throws Exception - Customer doesn't exists")
    void loadCustomerByUsername_Exception_Customer_Does_Not_Exists() {

        customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());
        customer.setPassword(TEST_STRING);
        given(customerRepository.findByEmail(TEST_STRING))
                .willReturn(Optional.empty());

        assertThrows(CustomException.class, () -> customerService.loadUserByUsername(TEST_STRING));

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