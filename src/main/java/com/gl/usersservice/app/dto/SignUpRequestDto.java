package com.gl.usersservice.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SignUpRequestDto implements Serializable {

    private static final long serialVersionUID = 3670772354167624987L;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email doesn't match the requirements")
    private String email;

    private String name;

    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.{8,12}$)(?=(.*\\d){2,})(?!.*\\d{3}|(?:.*\\d){3})(?!.*[A-Z]{2}|(?:.*[A-Z]){2})[\\d{2}A-Za-z]+$", message = "Password doesn't match the requirements")
    private String password;

    private List<PhoneDto> phones;

}
