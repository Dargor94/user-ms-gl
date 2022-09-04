package com.gl.usersservice.app.service;

import com.gl.usersservice.app.dto.CustomerResponseDto;
import com.gl.usersservice.app.dto.LoginRequestDto;
import com.gl.usersservice.app.dto.SignUpRequestDto;
import com.gl.usersservice.app.exception.CustomException;
import com.gl.usersservice.app.util.SecurityUtil;
import com.gl.usersservice.core.entity.Customer;
import com.gl.usersservice.core.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static com.gl.usersservice.app.exception.CustomException.ExceptionDefinition.CUSTOMER_ALREADY_EXISTS_ERROR;
import static com.gl.usersservice.app.exception.CustomException.ExceptionDefinition.CUSTOMER_NOT_FOUND_ERROR;
import static com.gl.usersservice.app.mapper.CustomerMapper.CUSTOMER_MAPPER;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponseDto signUp(SignUpRequestDto signUpRequestDto) throws CustomException {

        if (findByEmail(signUpRequestDto.getEmail()).isPresent())
            throw new CustomException(CUSTOMER_ALREADY_EXISTS_ERROR);

        Customer customer = CUSTOMER_MAPPER.toCustomer(signUpRequestDto);
        customer.setPassword(SecurityUtil.passwordEncoder().encode(customer.getPassword()));
        customer.setCreated(LocalDateTime.now());
        customer.setActive(true);

        String token = new SecurityUtil().generateToken(customer.getEmail());

        return CUSTOMER_MAPPER.toCustomerResponse(customerRepository.save(customer), token);
    }

    @Override
    public CustomerResponseDto login(LoginRequestDto loginRequestDto) throws CustomException {

        Customer customer = findByEmail(loginRequestDto.getEmail()).orElseThrow(this::getCustomerNotFoundException);
        customer.setLastLogin(LocalDateTime.now());
        customerRepository.save(customer);

        String newToken = new SecurityUtil().generateToken(customer.getEmail());

        return CUSTOMER_MAPPER.toCustomerResponse(customer, newToken);

    }

    private Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = findByEmail(email).orElseThrow(this::getCustomerNotFoundException);
        return new org.springframework.security.core.userdetails.User(
                email,
                customer.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }


    public CustomException getCustomerNotFoundException() {
        return new CustomException(CUSTOMER_NOT_FOUND_ERROR);
    }

}
