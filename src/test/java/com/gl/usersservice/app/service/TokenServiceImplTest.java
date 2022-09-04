package com.gl.usersservice.app.service;

import com.gl.usersservice.core.entity.Token;
import com.gl.usersservice.core.repository.TokenRepository;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TokenServiceImplTest {

    static final String TEST_STRING = "TEST";
    @MockBean
    TokenRepository tokenRepository;
    TokenServiceImpl tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenServiceImpl(tokenRepository);
    }

    @Test
    @DisplayName("Token_Is_In_Black_List")
    void verifyIsTokenInBlackList_True() {
        given(tokenRepository.getToken(TEST_STRING, TEST_STRING))
                .willReturn(Optional.of(new Token()));

        assertThrows(JwtException.class, () -> tokenService.verifyIsTokenInBlackList(TEST_STRING, TEST_STRING));
        verify(tokenRepository).getToken(TEST_STRING, TEST_STRING);
    }

    @Test
    @DisplayName("Token_Is_Not_In_Black_List")
    void verifyIsTokenInBlackList_False() {
        given(tokenRepository.getToken(TEST_STRING, TEST_STRING))
                .willReturn(Optional.empty());

        assertDoesNotThrow(() -> tokenService.verifyIsTokenInBlackList(TEST_STRING, TEST_STRING));
        verify(tokenRepository).getToken(TEST_STRING, TEST_STRING);
    }
    
    @Test
    @DisplayName("Token_Black_List_Update")
    void updateBlackListedToken() {

        Token token = new Token();
        token.setId(1L);

        doNothing().when(tokenRepository).deleteAllByUserName(TEST_STRING);

        when(tokenRepository.save(token))
                .thenReturn(any(Token.class));

        tokenService.updateBlackListedToken(TEST_STRING, TEST_STRING);

        verify(tokenRepository).deleteAllByUserName(TEST_STRING);
        verify(tokenRepository).save(any(Token.class));
    }
}