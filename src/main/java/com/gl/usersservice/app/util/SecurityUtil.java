package com.gl.usersservice.app.util;

import com.fasterxml.uuid.Generators;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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

    public String getUsernameFromToken(String token) {
        String userName;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            userName = claims.getSubject();
        } catch (Exception e) {
            userName = null;
        }
        return userName;
    }

    public String getIdFromToken(String token) {
        String id;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            id = claims.getId();
        } catch (Exception e) {
            id = null;
        }
        return id;
    }

    public String generateToken(String userName) {

        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(userName)
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

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userName = getUsernameFromToken(token);
        return (
                userName != null &&
                        userName.equals(userDetails.getUsername()) &&
                        !isTokenExpired(token)
        );
    }

    public boolean isTokenExpired(String token) {
        Date expireDate = getExpirationDate(token);
        return expireDate.before(new Date());
    }

    private Date getExpirationDate(String token) {
        Date expireDate;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
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
