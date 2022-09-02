package com.gl.usersservice.app.service;

import com.gl.usersservice.app.exception.CustomException;

public interface TokenService {

    boolean verifyIsTokenInBlackList(String identifier, String userName) throws CustomException;

    void updateBlackListedToken(String identifier, String userName);
}
