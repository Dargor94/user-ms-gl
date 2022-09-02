package com.gl.usersservice.app.service;

import com.gl.usersservice.app.mapper.TokenMapper;
import com.gl.usersservice.core.entity.Token;
import com.gl.usersservice.core.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public boolean verifyIsTokenInBlackList(String identifier, String userName) {
        Token token = TokenMapper.TOKEN_MAPPER.toToken(identifier, userName);
        return tokenRepository.getToken(token.getIdentifier(), token.getUserName()).isPresent();
    }

    @Override
    @Transactional
    public void updateBlackListedToken(String identifier, String userName) {
        tokenRepository.deleteAllByUserName(userName);
        tokenRepository.save(TokenMapper.TOKEN_MAPPER.toToken(identifier, userName));
    }
}
