package com.gl.usersservice.app.mapper;

import com.gl.usersservice.app.dto.PhoneDto;
import com.gl.usersservice.app.dto.SignUpRequestDto;
import com.gl.usersservice.app.dto.CustomerResponseDto;
import com.gl.usersservice.core.entity.Customer;
import com.gl.usersservice.core.entity.Phone;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CustomerMapper {

    CustomerMapper CUSTOMER_MAPPER = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "phones", source = "phones", qualifiedByName = "toPhones")
    Customer toCustomer(SignUpRequestDto signUpRequestDto);

    @Named("toPhones")
    @IterableMapping(qualifiedByName = "toPhone")
    List<Phone> toPhones(List<PhoneDto> phones);

    @Named("toPhone")
    @Mapping(target = "phoneId", ignore = true)
    @Mapping(target = "customer", ignore = true)
    Phone toPhone(PhoneDto phone);

    CustomerResponseDto toCustomerResponse(Customer customer, String token);

}
