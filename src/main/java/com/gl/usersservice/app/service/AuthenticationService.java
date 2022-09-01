package com.gl.usersservice.app.service;

import com.gl.usersservice.app.dto.LoginRequestDto;

public interface AuthenticationService {

     void authenticate(LoginRequestDto authInputToken);

}
