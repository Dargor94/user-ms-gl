package com.gl.usersservice.app.mapper;

import com.gl.usersservice.core.entity.Token;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TokenMapper {

    TokenMapper TOKEN_MAPPER = Mappers.getMapper(TokenMapper.class);

    @Mapping(target = "id", ignore = true)
    Token toToken(String identifier, String userName);

}
