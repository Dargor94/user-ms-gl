package com.gl.usersservice.core.repository;

import com.gl.usersservice.core.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = "SELECT t FROM Token t WHERE t.identifier = :#{#identifier} AND t.email = :#{#email}")
    Optional<Token> getToken(@Param("identifier") String identifier, @Param("email") String email);

    @Modifying
    @Query("DELETE FROM Token t WHERE t.email = :#{#email}")
    void deleteAllByUserName(@Param("email") String email);

}
