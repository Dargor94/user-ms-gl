package com.gl.usersservice.app.config;


import com.gl.usersservice.app.service.CustomerService;
import com.gl.usersservice.app.service.TokenService;
import com.gl.usersservice.app.util.SecurityUtil;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final CustomerService customerService;
    private final TokenService tokenService;

    private SecurityUtil securityUtil;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        String authToken = securityUtil.getToken(request);
        if (!ObjectUtils.isEmpty(authToken)) {
            String userName = securityUtil.getUsernameFromToken(authToken);
            String tokenIdentifier = securityUtil.getIdFromToken(authToken);
            if (!ObjectUtils.isEmpty(userName) && !ObjectUtils.isEmpty(tokenIdentifier)) {
                if (tokenService.verifyIsTokenInBlackList(tokenIdentifier, userName))
                    throw new JwtException("Token is invalid");
                UserDetails userDetails = customerService.loadUserByUsername(userName);
                if (securityUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    tokenService.updateBlackListedToken(tokenIdentifier, userName);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}
