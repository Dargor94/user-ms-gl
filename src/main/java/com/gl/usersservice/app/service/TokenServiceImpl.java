package com.gl.usersservice.app.service;

import com.gl.usersservice.app.mapper.TokenMapper;
import com.gl.usersservice.core.entity.Token;
import com.gl.usersservice.core.repository.TokenRepository;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public void verifyIsTokenInBlackList(String identifier, String userName) {
        Token token = TokenMapper.TOKEN_MAPPER.toToken(identifier, userName);
        tokenRepository.getToken(token.getIdentifier(), token.getUserName()).ifPresent(token1 -> {
            throw new JwtException("Token is invalid");
        });
    }

    @Override
    @Transactional
    public void updateBlackListedToken(String identifier, String userName) {
        tokenRepository.deleteAllByUserName(userName);
        tokenRepository.save(TokenMapper.TOKEN_MAPPER.toToken(identifier, userName));
    }
}
