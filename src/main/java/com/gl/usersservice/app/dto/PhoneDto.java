package com.gl.usersservice.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneDto {

    private long number;
    private int cityCode;
    private String countryCode;

}
