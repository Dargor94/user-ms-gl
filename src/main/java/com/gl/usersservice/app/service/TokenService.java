package com.gl.usersservice.app.service;

public interface TokenService {

    void verifyIsTokenInBlackList(String identifier, String userName);

    void updateBlackListedToken(String identifier, String userName);
}
