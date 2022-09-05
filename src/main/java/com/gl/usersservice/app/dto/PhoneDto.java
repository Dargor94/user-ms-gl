package com.gl.usersservice.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PhoneDto {

    private long number;
    private int cityCode;
    private String countryCode;

}
