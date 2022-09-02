package com.gl.usersservice.app.service;

public interface TokenService {

    boolean verifyIsTokenInBlackList(String identifier, String userName);

    void updateBlackListedToken(String identifier, String userName);
}
