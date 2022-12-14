package com.gl.usersservice.app.util;

import com.fasterxml.uuid.Generators;
import com.gl.usersservice.app.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.gl.usersservice.app.exception.CustomException.ExceptionDefinition.INVALID_TOKEN_ERROR;

@Component
public class SecurityUtil {

    private final String secret = "my-secret";
    private final String issuer = "my-company";
    private final int expiresIn = 3600;
    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }


    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String getSubjectFromToken(String token) {
        String subject;
        try {
            final Claims claims = getAllClaimsFromToken(token);
            subject = claims.getSubject();
        } catch (Exception e) {
            throw new JwtException("Token is invalid");
        }
        return subject;
    }

    public String getIdFromToken(String token) {
        String id;
        try {
            final Claims claims = getAllClaimsFromToken(token);
            id = claims.getId();
        } catch (Exception e) {
            throw new JwtException("Token is invalid");
        }
        return id;
    }

    public String generateToken(String email) {

        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setId(generateTokenId())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    private String generateTokenId() {
        return Generators.timeBasedGenerator().generate().toString();
    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + expiresIn * 1000);
    }

    public void validateToken(String token, String subject, UserDetails userDetails) throws CustomException {
        if (ObjectUtils.isEmpty(new Object[]{token, subject}) || !subject.equals(userDetails.getUsername()) || isTokenExpired(token))
            throw new CustomException(INVALID_TOKEN_ERROR);

    }

    public boolean isTokenExpired(String token) {
        Date expireDate = getExpirationDate(token);
        return expireDate.before(new Date());
    }

    private Date getExpirationDate(String token) {
        Date expireDate;
        try {
            final Claims claims = getAllClaimsFromToken(token);
            expireDate = claims.getExpiration();
        } catch (Exception e) {
            expireDate = null;
        }
        return expireDate;
    }

    public String getToken(HttpServletRequest request) {

        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

}
