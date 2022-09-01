package com.gl.usersservice.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SignUpResponseDto implements Serializable {

    private static final long serialVersionUID = -5621980805597019135L;
    private UUID customerId;
    private String email;
    private String name;
    private String password;
    private String token;

    @JsonFormat(pattern = "MMM dd, yyyy hh:mm a")
    private LocalDateTime created;

    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a")
    private LocalDateTime lastLogin;

    private boolean isActive;
    private List<PhoneDto> phones;


}
